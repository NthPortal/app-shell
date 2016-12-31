package com.nthportal.shell

trait LineParser {
  def parseLine(line: String): ImmutableSeq[String]
}
