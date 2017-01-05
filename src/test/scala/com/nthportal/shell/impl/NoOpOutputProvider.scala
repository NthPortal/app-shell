package com.nthportal.shell.impl

import com.nthportal.shell.OutputProvider

object NoOpOutputProvider extends OutputProvider {
  override def write(s: String): Unit = {}

  override def writeln(s: String): Unit = {}
}
