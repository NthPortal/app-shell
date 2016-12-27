package com.nthportal.shell
package core

trait Executable {
  def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit
}
