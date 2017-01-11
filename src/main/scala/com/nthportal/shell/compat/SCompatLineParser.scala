package com.nthportal.shell
package compat

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{LineParser => JLineParser}
import com.nthportal.shell.{LineParser => SLineParser}

private[compat] class SCompatLineParser(val parser: JLineParser) extends SLineParser {
  override def parseLineForExecution(line: String): ImmutableSeq[String] = {
    listToScalaImmutableSeq(parser.parseLineForExecution(line))
  }

  override def parseLineForTabCompletion(line: String): ImmutableSeq[String] = {
    listToScalaImmutableSeq(parser.parseLineForTabCompletion(line))
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: SCompatLineParser => parser == that.parser
    case _ => false
  }
}
