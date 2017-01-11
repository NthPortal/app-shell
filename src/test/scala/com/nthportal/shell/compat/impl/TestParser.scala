package com.nthportal.shell.compat
package impl

import java.util

object TestParser extends LineParser {
  val split = "\\s+"
  val end = "^.*" + split + "$"

  override def parseLineForExecution(line: String): util.List[String] = {
    val list = new util.ArrayList[String]()
    for (e <- line.split(split)) list.add(e)
    list
  }

  override def parseLineForTabCompletion(line: String): util.List[String] = {
    if (line.isEmpty) util.Arrays.asList("")
    else if (!line.matches(end)) parseLineForExecution(line)
    else {
      val list = parseLineForExecution(line)
      list.add("")
      list
    }
  }
}
