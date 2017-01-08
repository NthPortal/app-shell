lazy val shared = Seq(
  organization := "com.nthportal",
  version := "1.0.0-SNAPSHOT",

  scalaVersion := "2.12.1"
)

lazy val macros = (project in file("macros"))
  .settings(shared)
  .settings(
    name := "app-shell-macros",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val core = (project in file("core"))
  .dependsOn(macros)
  .settings(shared)
  .settings(
    name := "app-shell",

    coverageEnabled := true,

    libraryDependencies ++= Seq(
      "com.google.guava" % "guava" % "20.0",
      "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
      "org.scalatest" %% "scalatest" % "3.0.1" % Test
    )
  )
