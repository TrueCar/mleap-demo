package com.truecar.mleap.demo.server

import java.io.File

import com.truecar.mleap.bundle.zip.ZipBundleReader
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.serialization.json.DefaultJsonMleapSerializer

/**
  * Created by hwilkins on 1/20/16.
  */
object Boot extends App {
  val transformerPath = args(0)
  val bundleReader = ZipBundleReader(new File(transformerPath))
  val serializer = DefaultJsonMleapSerializer.createSerializer()
  val transformer = serializer.deserializeFromBundle(bundleReader).asInstanceOf[Transformer]

  MleapServer(transformer).start()
}
