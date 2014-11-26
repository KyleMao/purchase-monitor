lazy val root = (project in file(".")).
  settings(
    name := "purchase-monitor",
    version := "0.1",
    scalaVersion := "2.11.4",
    libraryDependencies += "com.typesafe" % "config" % "1.2.1"
  )
