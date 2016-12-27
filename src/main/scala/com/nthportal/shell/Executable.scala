package com.nthportal.shell

trait Executable {
  def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit
}
