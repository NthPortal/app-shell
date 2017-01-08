package com.nthportal.shell

/**
  * Parses input lines of a shell into a list of arguments.
  */
trait LineParser {
  /**
    * Parses a line into arguments, for the purpose of executing
    * the line.
    *
    * If the line ends in one or more argument separation characters,
    * the separation characters SHOULD be ignored, and the argument
    * preceding them SHOULD be the final argument. The list of arguments
    * returned SHOULD NOT end with an empty string.
    *
    * @param line the line to be parsed
    * @throws LineParseException if the line cannot be parsed
    *                            (e.g. if it is syntactically invalid)
    * @return a list of arguments from the input line
    */
  @throws[LineParseException]
  def parseLineForExecution(line: String): ImmutableSeq[String]

  /**
    * Parses a line into arguments, for the purpose of generating
    * a list of tab completions.
    *
    * If the line ends in one or more argument separation characters,
    * the final argument SHOULD be an empty string.
    *
    * @param line the line to be parsed
    * @throws LineParseException if the line cannot be parsed
    *                            (e.g. if it is syntactically invalid)
    * @return a list of arguments from the input line
    */
  @throws[LineParseException]
  def parseLineForTabCompletion(line: String): ImmutableSeq[String]
}
