package com.nthportal.shell
package util

import com.nthportal.shell.core.Executable

trait CommandExecutor extends CommandDelegator with Executable {
  protected def noArgExecution: Option[String]

  protected def noSuchCommandExecution(command: String, args: ImmutableSeq[String]): Option[String]

  override final def execute(args: ImmutableSeq[String]): Option[String] = args match {
    case Seq.empty => noArgExecution
    case command +: tail =>
      commandsByName.get(command)
        .map(_.execute(tail))
        .getOrElse(noSuchCommandExecution(command, tail))
  }
}
