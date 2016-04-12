package com.truecar.mleap.demo.server

import java.io.File

import ml.bundle.fs.DirectoryBundle
import ml.bundle.zip.ZipBundleReader
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.serialization.ml.v1.MlJsonSerializer

/**
  * Created by hwilkins on 1/20/16.
  */
object Boot extends App {
  val transformerPath = args(0)
  val bundleReader = DirectoryBundle(new File(transformerPath))
  val serializer = MlJsonSerializer
  val transformer = serializer.deserializeWithClass(bundleReader).asInstanceOf[Transformer]

  val port = if(args.length == 2) {
    args(1).toInt
  } else {
    8080
  }

  MleapServer(transformer, port).start()
}
