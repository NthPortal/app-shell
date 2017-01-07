package com.nthportal.shell
package parsers

class WhitespaceDelineatingParserTest extends SimpleSpec {
  behavior of "WhitespaceDelineatingParser"

  it should "parse lines correctly for execution" in {
    WhitespaceDelineatingParser.parseLineForExecution("a line") should have length 2
    WhitespaceDelineatingParser.parseLineForExecution("a\tline") should have length 2

    WhitespaceDelineatingParser.parseLineForExecution("a_line") should have length 1

    WhitespaceDelineatingParser.parseLineForExecution("a  line") should have length 2
    WhitespaceDelineatingParser.parseLineForExecution("a\t\tline") should have length 2
    WhitespaceDelineatingParser.parseLineForExecution("a \tline") should have length 2
    WhitespaceDelineatingParser.parseLineForExecution("a \t \tline") should have length 2

    WhitespaceDelineatingParser.parseLineForExecution("") should have length 0
    WhitespaceDelineatingParser.parseLineForExecution("word ") should have length 1
  }

  it should "parse lines correctly for tab completion" in {
    WhitespaceDelineatingParser.parseLineForTabCompletion("a line") should have length 2
    WhitespaceDelineatingParser.parseLineForTabCompletion("a\tline") should have length 2

    WhitespaceDelineatingParser.parseLineForTabCompletion("a_line") should have length 1

    WhitespaceDelineatingParser.parseLineForTabCompletion("a  line") should have length 2
    WhitespaceDelineatingParser.parseLineForTabCompletion("a\t\tline") should have length 2
    WhitespaceDelineatingParser.parseLineForTabCompletion("a \tline") should have length 2
    WhitespaceDelineatingParser.parseLineForTabCompletion("a \t \tline") should have length 2

    WhitespaceDelineatingParser.parseLineForTabCompletion("") should have length 1
    WhitespaceDelineatingParser.parseLineForTabCompletion("word ") should have length 2
  }
}
