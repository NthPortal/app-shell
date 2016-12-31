package com.nthportal.shell

trait OutputSink {
  def write(charSequence: CharSequence): Unit

  def write(any: Any): Unit = write(any.toString)

  def writeln(charSequence: CharSequence): Unit

  def writeln(any: Any): Unit = writeln(any.toString)
}
