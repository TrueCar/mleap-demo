package com.truecar.mleap.demo.server.service

import java.util.zip.ZipInputStream

import akka.stream.Materializer
import akka.stream.scaladsl.{Source, StreamConverters}
import akka.util.ByteString
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.demo.server.support.{TransformRequest, TransformResponse}
import com.truecar.mleap.serialization.ml.v1.MlJsonSerializer
import ml.bundle.zip.ZipBundleReader

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * Created by hwilkins on 1/20/16.
  */
class TransformerService(var transformer: Transformer)
                        (implicit materializer: Materializer,
                         ec: ExecutionContext) {
  def transform(request: TransformRequest): Try[TransformResponse] = {
    transformer.transform(request.frame).map(TransformResponse)
  }

  def updateTransformer(source: Source[ByteString, Any]): Future[Transformer] = {
    Future {
      val inputStream = source.runWith(StreamConverters.asInputStream())
      val zipReader = new ZipInputStream(inputStream)
      val zipBundleReader = ZipBundleReader(zipReader)
      val serializer = MlJsonSerializer
      transformer = serializer.deserializeWithClass(zipBundleReader).asInstanceOf[Transformer]
      transformer
    }
  }
}
