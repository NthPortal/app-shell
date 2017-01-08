package com.nthportal.shell

/**
  * Something to which output can be written (by a [[Shell]]).
  */
trait OutputSink {
  /**
    * Writes a string to this output sink.
    *
    * @param s the string to be written
    */
  def write(s: String): Unit

  /**
    * Writes an object to this output sink.
    *
    * @param any the object to be written
    */
  def write(any: Any): Unit = write(any.toString)

  /**
    * Writes a string to this output sink as a line.
    *
    * It is RECOMMENDED that this be equivalent to writing the string
    * followed by a newline character.
    *
    * @param s the string to be written
    */
  def writeln(s: String): Unit

  /**
    * Writes an object to this output sink as a line.
    *
    * It is RECOMMENDED that this be equivalent to writing the object
    * followed by a newline character.
    *
    * @param any the object to be written
    */
  def writeln(any: Any): Unit = writeln(any.toString)
}
