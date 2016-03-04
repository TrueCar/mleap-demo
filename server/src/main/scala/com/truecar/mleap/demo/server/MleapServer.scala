package com.truecar.mleap.demo.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.truecar.mleap.runtime.transformer.Transformer
import com.truecar.mleap.demo.server.resource.TransformerResource
import com.truecar.mleap.demo.server.service.TransformerService

/**
  * Created by hwilkins on 1/20/16.
  */
case class MleapServer(transformer: Transformer) {
  def start(): Unit = {
    implicit val system = ActorSystem("mleap-transformer")
    implicit val materializer = ActorMaterializer()
    implicit val ec = system.dispatcher

    val transformerService = TransformerService(transformer)
    val transformerResource = TransformerResource(transformerService)
    val routes = transformerResource.routes

    Http().bindAndHandle(routes, "localhost", 8080)
  }
}
