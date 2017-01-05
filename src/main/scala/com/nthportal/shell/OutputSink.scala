package com.nthportal.shell

trait OutputSink {
  def write(s: String): Unit

  def write(any: Any): Unit = write(any.toString)

  def writeln(s: String): Unit

  def writeln(any: Any): Unit = writeln(any.toString)
}
