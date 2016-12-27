package com.nthportal.shell.core

import com.nthportal.shell.ImmutableSeq

trait LineParser {
  def parseLine(line: String): ImmutableSeq[String]
}
