package com.nthportal.example.shell.console

import com.nthportal.shell.OutputProvider

object ConsoleOutputProvider extends OutputProvider {
  override def write(s: String): Unit = print(s)

  override def writeln(s: String): Unit = println(s)
}
