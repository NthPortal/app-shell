package com.nthportal.shell
package util

import com.nthportal.shell.core.Command

import scala.collection.immutable

trait CommandDelegator {
  protected final val commandsByName: immutable.Map[String, Command] =
    commands.toStream.map(c => c.name -> c).toMap

  protected def commands: ImmutableSeq[Command]
}
