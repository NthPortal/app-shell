package com.nthportal.shell
package compat

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{LineParser => JLineParser}
import com.nthportal.shell.{LineParser => SLineParser}

private[compat] case class SCompatLineParser(parser: JLineParser) extends SLineParser {
  override def parseLineForExecution(line: String): ImmutableSeq[String] = {
    listToScalaImmutableSeq(parser.parseLineForExecution(line))
  }

  override def parseLineForTabCompletion(line: String): ImmutableSeq[String] = {
    listToScalaImmutableSeq(parser.parseLineForTabCompletion(line))
  }
}
