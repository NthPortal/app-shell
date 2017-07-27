package com.nthportal.shell
package parsers

/**
  * A [[LineParser]] which separates arguments with whitespace.
  */
object WhitespaceDelineatingParser extends LineParser {
  private val patternStr = """\s+"""
  private val endPattern = ("^.*" + patternStr + "$").r.pattern
  private val splitPattern = patternStr.r

  override def parseLineForExecution(line: String): ImmutableSeq[String] = {
    if (line.isEmpty) Nil
    else splitPattern.split(line).to[ImmutableSeq]
  }

  override def parseLineForTabCompletion(line: String): ImmutableSeq[String] = {
    if (line.isEmpty) List("")
    else if (endPattern.matcher(line).matches()) parseLineForExecution(line) :+ ""
    else parseLineForExecution(line)
  }
}
