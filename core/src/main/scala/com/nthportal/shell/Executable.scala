package com.nthportal.shell

/**
  * Something which can be executed by a [[Shell]].
  */
trait Executable {
  /**
    * Executes this with the given arguments.
    *
    * @param args the arguments with which this should be executed
    * @param sink an [[OutputSink]] to which output may be written during execution
    */
  def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit
}
