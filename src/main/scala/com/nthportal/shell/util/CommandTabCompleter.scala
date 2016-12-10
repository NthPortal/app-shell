package com.nthportal.shell
package util

import com.nthportal.shell.core.TabCompletable

trait CommandTabCompleter extends CommandDelegator with TabCompletable {
  override final def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = args match {
    case Seq.empty => Nil
    case Seq(prefix) =>
      commands
        .toStream
        .map(_.name)
        .filter(_.startsWith(prefix))
    case command +: tail =>
      commandsByName.get(command)
        .map(_.tabComplete(tail))
        .getOrElse(Nil)
  }
}
