package com.truecar.mleap.demo.server

import java.io.File

import com.truecar.mleap.core.serialization.JsonSerializationSupport._
import com.truecar.mleap.runtime.serialization.RuntimeJsonSupport._
import com.truecar.mleap.runtime.transformer.Transformer

/**
  * Created by hwilkins on 1/20/16.
  */
object Boot extends App {
  val transformerPath = args(0)
  val transformer = new File(transformerPath).parseTo[Transformer].get

  MleapServer(transformer).start()
}
