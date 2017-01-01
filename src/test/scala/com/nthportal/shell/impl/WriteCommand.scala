package com.nthportal.shell
package impl

object WriteCommand extends Command {
  override val name: String = "write"

  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = {
    sink.writeln(s"${this.getClass.getName} has been executed")
  }
}
