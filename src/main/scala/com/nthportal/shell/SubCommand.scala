package com.nthportal.shell

/**
  * A sub-command to be executed by a [[Shell]]. It may be a top-level [[Command command]],
  * a sub-command of a top-level command, or a sub-command of another sub-command.
  */
trait SubCommand extends Executable with TabCompletable {
  /**
    * Returns an [[Option]] containing a brief description of this sub-command,
    * or [[None]] if no description is provided.
    *
    * @return a brief description of this sub-command
    */
  def description: Option[String] = None

  /**
    * Returns an [[Option]] containing a help message for this sub-command,
    * or [[None]] if no help message is provided.
    *
    * The message may be based on the arguments provided, or it may be a
    * static message regardless of the arguments provided. For example, it may
    * return a different message for different sub-command of its own.
    *
    * @param args the arguments of this command for which to get a help message
    * @return a help message for this sub-command with the given arguments
    */
  def help(args: ImmutableSeq[String]): Option[String] = None

  def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = Nil

  /**
    * Executes this sub-command with the given arguments.
    *
    * @param args the arguments with which this should be executed
    * @param sink an [[OutputSink]] to which output may be written during execution
    */
  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink)
}
