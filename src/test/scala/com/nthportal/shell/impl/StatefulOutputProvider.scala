package com.nthportal.shell
package impl

import scala.collection.mutable

class StatefulOutputProvider extends OutputProvider {
  private var _writtenTo = false
  private val linesWritten = mutable.Buffer[String]()

  private var currentLine = StringBuilder.newBuilder

  def writtenTo: Boolean = _writtenTo

  def lines: List[String] = linesWritten.toList

  override def write(charSequence: CharSequence): Unit = {
    _writtenTo = true
    currentLine ++= charSequence.toString
  }

  override def writeln(charSequence: CharSequence): Unit = {
    write(charSequence)
    linesWritten += currentLine.mkString
    currentLine.clear()
  }
}
