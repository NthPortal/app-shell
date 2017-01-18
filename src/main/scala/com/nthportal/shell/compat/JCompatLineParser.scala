package com.nthportal.shell
package compat

import java.util

import com.nthportal.shell.compat.{LineParser => JLineParser}
import com.nthportal.shell.{LineParser => SLineParser}

import scala.collection.JavaConverters

private[compat] case class JCompatLineParser(parser: SLineParser) extends JLineParser {
  override def parseLineForExecution(line: String): util.List[String] = {
    JavaConverters.seqAsJavaList(parser.parseLineForExecution(line))
  }

  override def parseLineForTabCompletion(line: String): util.List[String] = {
    JavaConverters.seqAsJavaList(parser.parseLineForTabCompletion(line))
  }
}
