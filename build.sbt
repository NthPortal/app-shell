organization := "com.nthportal"
name := "app-shell"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.1"

coverageEnabled := true

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "20.0",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)
