lazy val root = (project in file(".")).
  settings(
    name := "purchase-monitor",
    version := "0.1",
    scalaVersion := "2.10.3",
    libraryDependencies ++= Seq(
        "com.typesafe" % "config" % "1.2.1",
        "org.scalanlp" % "breeze_2.10" % "0.10",
        "org.scalanlp" % "breeze-natives_2.10" % "0.10",
        "org.scalanlp" % "breeze-viz_2.10" % "0.9"
    )
  )
