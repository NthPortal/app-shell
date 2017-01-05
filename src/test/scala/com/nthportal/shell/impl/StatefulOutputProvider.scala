package com.nthportal.shell
package impl

import scala.collection.mutable

class StatefulOutputProvider extends OutputProvider {
  import StatefulOutputProvider._

  private var _writtenTo = false
  private val linesWritten = mutable.Buffer[String]()

  private var currentLine = StringBuilder.newBuilder

  def writtenTo: Boolean = _writtenTo

  def lines: List[String] = linesWritten.toList

  def state: State = State(_writtenTo, lines, currentLine.mkString)

  override def write(charSequence: CharSequence): Unit = {
    if (charSequence.toString.nonEmpty) {
      _writtenTo = true
      currentLine ++= charSequence.toString
    }
  }

  override def writeln(charSequence: CharSequence): Unit = {
    write(charSequence)
    linesWritten += currentLine.mkString
    currentLine.clear()
  }
}

object StatefulOutputProvider {
  def apply(): StatefulOutputProvider = new StatefulOutputProvider()

  case class State(writtenTo: Boolean, lines: List[String], currentLine: String)
}
