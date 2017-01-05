package com.nthportal.shell
package parsers

import java.util.regex.Pattern

object WhitespaceDelineatingParser extends LineParser {
  private val patternStr = "\\s+"
  private val endPattern = Pattern.compile("^.*" + patternStr + "$")
  private val splitPattern = Pattern.compile(patternStr)

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
