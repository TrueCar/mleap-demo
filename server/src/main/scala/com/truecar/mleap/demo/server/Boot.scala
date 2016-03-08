package com.truecar.mleap.demo.server

import java.io.File

import ml.bundle.zip.ZipBundleReader
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.serialization.ml.json.MlSimpleJsonSerializer

/**
  * Created by hwilkins on 1/20/16.
  */
object Boot extends App {
  val transformerPath = args(0)
  val bundleReader = ZipBundleReader(new File(transformerPath))
  val serializer = MlSimpleJsonSerializer
  val transformer = serializer.deserializeWithClass(bundleReader).asInstanceOf[Transformer]

  MleapServer(transformer).start()
}
