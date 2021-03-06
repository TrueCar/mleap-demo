import sbt._
import Keys._

object Common {
  val appVersion = "0.1-SNAPSHOT"

  var settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    scalaVersion := "2.11.7",
    organization := "com.truecar.mleap",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
}