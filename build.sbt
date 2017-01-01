organization := "com.nthportal"
name := "app-shell"
version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.1"

coverageEnabled := true

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "20.0",
  "com.typesafe.akka" %% "akka-agent" % "2.4.14",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)
