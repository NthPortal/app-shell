package com.nthportal.shell
package util

trait CommandTabCompleter extends CommandDelegator with TabCompletable {
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
