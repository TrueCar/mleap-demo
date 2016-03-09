package com.truecar.mleap.demo.server.support

import com.truecar.mleap.serialization.mleap.v1.MleapJsonSupport._
import spray.json.DefaultJsonProtocol

/**
  * Created by hwilkins on 1/20/16.
  */
trait TransformerJsonSupport extends DefaultJsonProtocol {
  implicit val transformRequestFormat = jsonFormat1(TransformRequest)

  implicit val transformResponseFormat = jsonFormat1(TransformResponse)
}
object TransformerJsonSupport extends TransformerJsonSupport
