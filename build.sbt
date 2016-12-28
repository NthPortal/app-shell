organization := "com.nthportal"
name := "scala-shell"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "20.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)
