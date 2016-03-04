import sbt._

object Dependencies {
  val sparkVersion = "1.6.0"
  val mleapVersion = "0.1-SNAPSHOT"
  val akkaVersion = "2.4.1"
  val akkaStreamVersion = "2.0.1"

  lazy val sparkDependencies = Seq(
    ("org.apache.spark" %% "spark-core" % sparkVersion)
      .exclude("org.mortbay.jetty", "servlet-api")
      .exclude("commons-beanutils", "commons-beanutils-core")
      .exclude("commons-collections", "commons-collections")
      .exclude("commons-logging", "commons-logging")
      .exclude("com.esotericsoftware.minlog", "minlog"),
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-mllib" % sparkVersion,
    "org.apache.spark" %% "spark-catalyst" % sparkVersion).map(_ % "provided")

  lazy val demoDependencies = sparkDependencies
    .union(Seq("com.truecar.mleap" %% "mleap-spark" % mleapVersion,
      "com.databricks" %% "spark-csv" % "1.3.0"))

  lazy val serverDependencies = Seq("com.truecar.mleap" %% "mleap-runtime" % mleapVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaStreamVersion)
}