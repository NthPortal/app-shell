package com.nthportal.example.shell.console

import com.nthportal.shell.{Command, ImmutableSeq, OutputSink, Shell}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

class ConsoleShell {
  private var closed = false
  private val buf = StringBuilder.newBuilder

  private val exitCommand = new Command {
    override val name: String = "exit"

    override val description: Option[String] = Some("terminates the shell")

    override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = {
      println("Terminating Shell")
      closed = true
    }
  }

  private val shell = Shell(WhitespaceDelineatingParser, ConsoleOutputProvider, exitCommand)

  def readInput(): Unit = {
    println("Test Shell")
    print("> ")
    do {
      readNextChar()
    } while (!closed)
  }

  private def readNextChar(): Unit = {
    Console.in.read().toChar match {
      case '\n' =>
        println()
        executeLine()
        print("> ")
      case '\t' => tabComplete()
      case c =>
        buf.append(c)
        print(c)
    }
  }

  private def executeLine(): Unit = {
    val line = buf.mkString
    buf.clear()
    if (line.nonEmpty) shell.executeLine(line)
  }

  private def tabComplete(): Unit = {
    println()
    val line = buf.mkString
    println(shell.tabComplete(line).mkString("\t"))
    print(line)
  }

  private def handleEOF(): Unit = if (buf.isEmpty) closed = true
}
