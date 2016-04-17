package com.truecar.mleap.demo

import java.io.File

import _root_.ml.bundle.fs.DirectoryBundle
import com.esotericsoftware.kryo.io.Output
import com.truecar.mleap.serialization.ml.v1.MlJsonSerializer
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.spark.MleapSparkSupport._
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.feature.{StandardScaler, StringIndexer, VectorAssembler}
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by hwilkins on 3/1/16.
  */
object TrainDemoPipeline extends App {
  val sparkConfig = new SparkConf()
    .setAppName("Train Demo Pipeline")
    .setMaster("local[2]")
  val sc = new SparkContext(sparkConfig)
  val sqlContext = new SQLContext(sc)

  val inputPath = args(0)
  val mleapOutputPath = args(1)

  val inputSchema = StructType(Seq(
    StructField("id", LongType, nullable = true),
    StructField("name", StringType, nullable = true),
    StructField("space", StringType, nullable = true),
    StructField("price", DoubleType, nullable = true),
    StructField("bathrooms", DoubleType, nullable = true),
    StructField("bedrooms", DoubleType, nullable = true),
    StructField("room_type", StringType, nullable = true),
    StructField("square_feet", DoubleType, nullable = true),
    StructField("host_is_superhost", DoubleType, nullable = true),
    StructField("city", StringType, nullable = true),
    StructField("state", StringType, nullable = true),
    StructField("cancellation_policy", StringType, nullable = true),
    StructField("security_deposit", DoubleType, nullable = true),
    StructField("cleaning_fee", DoubleType, nullable = true),
    StructField("extra_people", DoubleType, nullable = true),
    StructField("minimum_nights", LongType, nullable = true),
    StructField("first_review", StringType, nullable = true),
    StructField("instant_bookable", DoubleType, nullable = true),
    StructField("number_of_reviews", LongType, nullable = true),
    StructField("review_scores_rating", DoubleType, nullable = true),
    StructField("price_per_bedroom", DoubleType, nullable = true)
  ))

  // Step 1. Load our Airbnb dataset with a nice schema
  var dataset = sqlContext.read.format("com.databricks.spark.csv")
    .options(Map("header" -> "true",
      "mode" -> "DROPMALFORMED",
      "nullValue" -> ""))
    .schema(inputSchema)
    .load(inputPath)

  // Step 2. Create our feature pipeline and train it on the entire dataset
  val continuousFeatures = Array("bathrooms",
    "bedrooms",
    "security_deposit",
    "cleaning_fee",
    "extra_people",
    "number_of_reviews",
    "review_scores_rating")
  val categoricalFeatures = Array("room_type",
    "host_is_superhost",
    "cancellation_policy",
    "instant_bookable")
  val allFeatures = continuousFeatures.union(categoricalFeatures)

  // Filter all null values
  val allCols = allFeatures.union(Seq("price")).map(dataset.col)
  val nullFilter = allCols.map(_.isNotNull).reduce(_ && _)
  dataset = dataset.select(allCols: _*).filter(nullFilter).persist()
  val Array(trainingDataset, validationDataset) = dataset.randomSplit(Array(0.7, 0.3))

  val continuousFeatureAssembler = new VectorAssembler().
    setInputCols(continuousFeatures).
    setOutputCol("unscaled_continuous_features")
  val continuousFeatureScaler = new StandardScaler().
    setInputCol("unscaled_continuous_features").
    setOutputCol("scaled_continuous_features")

  val categoricalFeatureIndexers = categoricalFeatures.map {
    feature => new StringIndexer().
      setInputCol(feature).
      setOutputCol(s"${feature}_index")
  }

  val featureCols = categoricalFeatureIndexers.map(_.getOutputCol).union(Seq("scaled_continuous_features"))
  val featureAssembler = new VectorAssembler().
    setInputCols(featureCols).
    setOutputCol("features")
  val estimators: Array[PipelineStage] = Array(continuousFeatureAssembler, continuousFeatureScaler).
    union(categoricalFeatureIndexers).
    union(Seq(featureAssembler))
  val featurePipeline = new Pipeline().
    setStages(estimators)
  val sparkFeaturePipelineModel = featurePipeline.fit(dataset)

  // Step 3. Create our random forest model
  val randomForest = new RandomForestRegressor().
    setFeaturesCol("features").
    setLabelCol("price").
    setPredictionCol("price_prediction")

  // Step 4. Assemble the final pipeline by implicit conversion to MLeap models
  val sparkPipelineEstimator = new Pipeline().setStages(Array(sparkFeaturePipelineModel, randomForest))
  val sparkPipeline = sparkPipelineEstimator.fit(trainingDataset)
  val mleapPipeline: Transformer = sparkPipeline

  // Step 5. Save our MLeap pipeline to a file
  val mleapFile = new File(mleapOutputPath)
  val bundleWriter = DirectoryBundle(mleapFile)
  mleapFile.mkdirs()
  //  mleapFile.mkdirs()
  //  val bundle = DirectoryBundle(mleapFile)
  val serializer = MlJsonSerializer
  serializer.serializeWithClass(mleapPipeline, bundleWriter)
  //  bundleWriter.out.close()
  //  val outputStream = FileSystem.get(new Configuration()).create(new Path(mleapOutputPath), true)
  //  mleapPipeline.serializeToStream(outputStream)
  //  outputStream.close()

  // Step 6. If specified, output a Kryo version of the original Spark pipeline

  if(args.length == 3) {
    val sparkOutputPath = args(2)

    val sparkSerializer = SparkSerializer()
    val fs = FileSystem.get(sc.hadoopConfiguration)
    val sparkOutputHdPath = new Path(sparkOutputPath)
    val output = new Output(fs.create(sparkOutputHdPath))
    sparkSerializer.write(sparkPipeline, output)
    output.close()
  }

  sc.stop()
}
