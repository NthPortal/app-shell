package com.nthportal.shell
package parsers

object WhitespaceDelineatingParser extends LineParser {
  override def parseLine(line: String): ImmutableSeq[String] = line.split("\\s").to[ImmutableSeq]
}
