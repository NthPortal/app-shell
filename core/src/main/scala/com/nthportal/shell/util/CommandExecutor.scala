package com.nthportal.shell
package util

/**
  * Delegates the execution of a [[Command]] to one of a sequence of commands
  * based on the arguments with which this is executed. That is, if the first
  * argument with which this is executed is the name of a command (in
  * [[CommandDelegator.commands]]), it will execute that command with the
  * subsequent arguments.
  */
trait CommandExecutor extends CommandDelegator with Executable {
  /**
    * The action (or "execution") to perform if this is executed with no
    * arguments.
    *
    * @param sink the [[OutputSink]] to which to write output
    */
  protected def noArgExecution(implicit sink: OutputSink): Unit

  /**
    * The execution to perform if the first argument with which this was
    * executed does not match the name of any command in [[CommandDelegator.commands]].
    *
    * @param command the first argument with which this was executed, which is
    *                not a valid command
    * @param args    the remaining arguments with which this was executed
    * @param sink    the [[OutputSink]] to which to write output
    */
  protected def noSuchCommandExecution(command: String, args: ImmutableSeq[String])
                                      (implicit sink: OutputSink): Unit

  /**
    * @inheritdoc
    */
  override final def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = args match {
    case Seq() => noArgExecution
    case name +: subArgs =>
      commandsByName.get(name) match {
        case Some(command) => command.execute(subArgs)
        case None => noSuchCommandExecution(name, subArgs)
      }
  }
}
