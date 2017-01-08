package com.nthportal.shell
package util

/**
  * Delegates the tab-completion of a [[Command]] to one of a sequence of commands
  * based on the arguments with which this is executed. That is, if the first
  * argument with which the tab-completion was requested is the name of a command
  * (in [[CommandDelegator.commands]]), it will delegate the tab-completion to
  * that command with the subsequent arguments.
  */
trait CommandTabCompleter extends CommandDelegator with TabCompletable {
  /**
    * @inheritdoc
    */
  override final def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = args match {
    case Seq() => Nil
    case Seq(prefix) =>
      commands
        .toStream
        .map(_.name)
        .filter(_.startsWith(prefix))
    case command +: subArgs =>
      commandsByName.get(command)
        .map(_.tabComplete(subArgs))
        .getOrElse(Nil)
  }
}
