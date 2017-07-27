organization := "com.nthportal"
name := "app-shell"
description := "A framework for creating shells/CLIs for an application."

val rawVersion = "1.2.1"
isSnapshot := true
version := rawVersion + {if (isSnapshot.value) "-SNAPSHOT" else ""}

scalaVersion := "2.12.2"
crossScalaVersions := Seq(
  "2.12.1",
  "2.12.2"
)

locally {jacoco.settings}

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.+",
  "com.nthportal" %% "extra-predef" % "1.1+",
  "com.nthportal" %% "future-queue" % "1.+",
  "com.google.guava" % "guava" % "21.+",
  "org.scalatest" %% "scalatest" % "3.0.+" % Test
)

scalacOptions ++= {
  if (isSnapshot.value) Seq()
  else scalaVersion.value split '.' map { _.toInt } match {
    case Array(2, 12, patch) if patch <= 2 => Seq("-opt:l:project")
    case Array(2, 12, patch) if patch > 2 => Seq("-opt:l:inline")
    case _ => Seq()
  }
}

publishTo := {
  if (isSnapshot.value) Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else None
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
