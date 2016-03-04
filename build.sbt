name := "mleap-demo"

lazy val `demo` = project.in(file("demo"))
  .settings(Common.settings)
  .settings(scalaVersion := "2.10.6")
  .settings(crossScalaVersions := Seq("2.10.6", "2.11.7"))
  .settings(libraryDependencies ++= Dependencies.demoDependencies)

lazy val `server` = project.in(file("server"))
  .settings(Common.settings)
  .settings(scalaVersion := "2.11.7")
  .settings(libraryDependencies ++= Dependencies.serverDependencies)
