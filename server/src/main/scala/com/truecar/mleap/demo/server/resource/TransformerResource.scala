package com.truecar.mleap.demo.server.resource

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.truecar.mleap.runtime.LocalLeapFrame
import com.truecar.mleap.serialization.mleap.v1.MleapJsonSupport._
import com.truecar.mleap.demo.server.service.TransformerService
import akka.http.scaladsl.server.Directives._
import com.truecar.mleap.demo.server.support.{TransformRequest, TransformerJsonSupport}

import scala.concurrent.ExecutionContext

/**
  * Created by hwilkins on 1/20/16.
  */
case class TransformerResource(service: TransformerService)
                              (implicit ec: ExecutionContext)
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
  } ~ path("transformer") {
    put {
      extractRequest {
        request =>
          complete {
            service.updateTransformer(request.entity.dataBytes).map(_ => "OK")
          }
      }
    }
  }
}
