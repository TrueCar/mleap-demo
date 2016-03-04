package com.truecar.mleap.demo.server.support

import com.truecar.mleap.runtime.LocalLeapFrame

/**
  * Created by hwilkins on 1/20/16.
  */
case class TransformRequest(frame: LocalLeapFrame)
case class TransformResponse(frame: LocalLeapFrame)
