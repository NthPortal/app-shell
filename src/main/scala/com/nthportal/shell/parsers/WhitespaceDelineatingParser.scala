package com.nthportal.shell
package parsers

import java.util.regex.Pattern

/**
  * A [[LineParser]] which separates arguments with whitespace.
  */
object WhitespaceDelineatingParser extends LineParser {
  private val patternStr = "\\s+"
  private val endPattern = Pattern.compile("^.*" + patternStr + "$")
  private val splitPattern = Pattern.compile(patternStr)

  /**
    * @inheritdoc
    */
  override def parseLineForExecution(line: String): ImmutableSeq[String] = {
    if (line.isEmpty) Nil
    else splitPattern.split(line).to[ImmutableSeq]
  }

  /**
    * @inheritdoc
    */
  override def parseLineForTabCompletion(line: String): ImmutableSeq[String] = {
    if (line.isEmpty) List("")
    else if (endPattern.matcher(line).matches()) parseLineForExecution(line) :+ ""
    else parseLineForExecution(line)
  }
}
