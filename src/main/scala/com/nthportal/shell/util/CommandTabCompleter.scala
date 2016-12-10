package com.nthportal.shell.util

import com.nthportal.shell.core.{Command, TabCompletable}

trait CommandTabCompleter extends TabCompletable {
  protected final val commandsByName: Map[String, Command] = commands.toStream.map(c => c.name -> c).toMap

  protected def commands: Seq[Command]

  override final def tabComplete(args: Seq[String]): Seq[String] = args match {
    case Seq.empty => Seq.empty
    case Seq(prefix) =>
      commands
        .toStream
        .map(_.name)
        .filter(_.startsWith(prefix))
    case command +: tail =>
      commandsByName.get(command)
        .map(_.tabComplete(tail))
        .getOrElse(Seq.empty)
  }
}
