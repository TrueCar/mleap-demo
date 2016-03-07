package com.truecar.mleap.demo.server.resource

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.truecar.mleap.runtime.LocalLeapFrame
import com.truecar.mleap.serialization.mleap.MleapJsonSupport._
import com.truecar.mleap.demo.server.service.TransformerService
import akka.http.scaladsl.server.Directives._
import com.truecar.mleap.demo.server.support.{TransformerJsonSupport, TransformRequest}

/**
  * Created by hwilkins on 1/20/16.
  */
case class TransformerResource(service: TransformerService)
  extends SprayJsonSupport with TransformerJsonSupport {
  val routes = path("transform") {
    post {
      entity(as[LocalLeapFrame]) {
        frame =>
          complete {
            service.transform(TransformRequest(frame)).get
          }
      }
    }
  }
}
