package com.nthportal.shell.impl

import com.nthportal.shell.OutputProvider

object NoOpOutputProvider extends OutputProvider {
  override def write(charSequence: CharSequence): Unit = {}

  override def writeln(charSequence: CharSequence): Unit = {}
}
