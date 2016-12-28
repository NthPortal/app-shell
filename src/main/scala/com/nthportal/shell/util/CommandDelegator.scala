package com.nthportal.shell
package util

import scala.collection.immutable

trait CommandDelegator {
  protected def commands: ImmutableSeq[Command]

  /**
    * Field must be lazy so that [[commands `commands`]]
    * is not null when this field is initialized.
    * */
  protected lazy final val commandsByName: immutable.Map[String, Command] =
    commands.toStream.map(c => c.name -> c).toMap
}
