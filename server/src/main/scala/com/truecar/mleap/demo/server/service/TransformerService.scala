package com.truecar.mleap.demo.server.service

import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.demo.server.support.{TransformResponse, TransformRequest}

import scala.util.Try

/**
  * Created by hwilkins on 1/20/16.
  */
case class TransformerService(transformer: Transformer) {
  def transform(request: TransformRequest): Try[TransformResponse] = {
    transformer.transform(request.frame).map(TransformResponse)
  }
}
