name := "mleap-demo"

lazy val `demo` = project.in(file("demo"))
  .settings(Common.settings)
  .settings(libraryDependencies ++= Dependencies.demoDependencies)

lazy val `server` = project.in(file("server"))
  .settings(Common.settings)
  .settings(libraryDependencies ++= Dependencies.serverDependencies)
