import sbt._
import Keys._

object Common {
  val appVersion = "0.1-SNAPSHOT"

  var settings: Seq[Def.Setting[_]] = Seq(
    version := appVersion,
    organization := "com.truecar.mleap",
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
}