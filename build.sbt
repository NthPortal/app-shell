organization := "com.nthportal"
name := "app-shell"
description := "A framework for creating shells/CLIs for an application."

val rawVersion = "1.2.0"
isSnapshot := false
version := rawVersion + {if (isSnapshot.value) "-SNAPSHOT" else ""}

scalaVersion := "2.12.1"
crossScalaVersions := Seq(
  "2.12.1"
)

locally {jacoco.settings}

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % "21.+",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.+",
  "com.nthportal" %% "future-queue" % "1.+",
  "org.scalatest" %% "scalatest" % "3.0.+" % Test
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true
licenses := Seq("The Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/NthPortal/app-shell"))

pomExtra :=
  <scm>
    <url>https://github.com/NthPortal/app-shell</url>
    <connection>scm:git:git@github.com:NthPortal/app-shell.git</connection>
    <developerConnection>scm:git:git@github.com:NthPortal/app-shell.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>NthPortal</id>
        <name>NthPortal</name>
        <url>https://github.com/NthPortal</url>
      </developer>
    </developers>
