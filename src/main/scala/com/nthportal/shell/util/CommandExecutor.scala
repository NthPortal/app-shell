package com.nthportal.shell
package util

trait CommandExecutor extends CommandDelegator with Executable {
  protected def noArgExecution(implicit sink: OutputSink): Unit

  protected def noSuchCommandExecution(command: String, args: ImmutableSeq[String])
                                      (implicit sink: OutputSink): Unit

  override final def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = args match {
    case Seq() => noArgExecution
    case name +: subArgs =>
      commandsByName.get(name) match {
        case Some(command) => command.execute(subArgs)
        case None => noSuchCommandExecution(name, subArgs)
      }
  }
}
