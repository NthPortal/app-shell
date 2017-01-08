package com.nthportal.shell

/**
  * An exception thrown when a [[LineParser]] is unable to parse a line.
  *
  * @param parseError a description of the parsing error
  * @param line the line which could not be parsed
  */
class LineParseException(parseError: String, line: String)
  extends Exception(s"Error `$parseError` when parsing line: $line")
