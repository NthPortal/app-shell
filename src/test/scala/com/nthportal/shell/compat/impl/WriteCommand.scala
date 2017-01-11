package com.nthportal.shell.compat
package impl

import java.util

import com.nthportal.shell.OutputSink

object WriteCommand extends Command {
  override def name: String = "write"

  override def execute(args: util.List[String],  sink: OutputSink): Unit = {
    sink.writeln(s"${this.getClass.getName} has been executed")
  }
}
