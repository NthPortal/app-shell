package com.nthportal.shell

trait SubCommand extends Executable with TabCompletable {
  def description: Option[String] = None

  def help(args: ImmutableSeq[String]): Option[String] = None

  def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = Nil
}
