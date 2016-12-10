package com.nthportal.shell.util

import com.nthportal.shell.core.Command

trait CommandDelegator {
  protected final val commandsByName: Map[String, Command] =
    commands.toStream.map(c => c.name -> c).toMap

  protected def commands: Seq[Command]
}
