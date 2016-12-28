package com.nthportal.example.shell.console

import com.nthportal.shell.OutputProvider

object ConsoleOutputProvider extends OutputProvider {
  override def write(charSequence: CharSequence): Unit = print(charSequence)

  override def writeln(charSequence: CharSequence): Unit = println(charSequence)
}
