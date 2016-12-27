package com.nthportal.shell.core

trait OutputSink {
  private implicit val defaultSeparator = ""

  def write(charSequence: CharSequence): Unit

  def write(any: Any): Unit = write(any.toString)

  def write(as: Any*)(implicit separator: String): Unit = write(as.mkString(separator))

  def writeln(charSequence: CharSequence): Unit

  def writeln(any: Any): Unit = writeln(any.toString)

  def writeln(as: Any*)(implicit separator: String): Unit = writeln(as.mkString(separator))
}
