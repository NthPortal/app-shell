package com.nthportal.shell
package util

import scala.collection.immutable.SortedMap

trait CommandDelegator {
  protected def commands: ImmutableIterable[Command]

  // Field must be lazy so that `commands` is not null when this field is initialized.
  /**
    * A mapping of each command's name to itself.
    */
  protected lazy final val commandsByName: Map[String, Command] = {
    val b = SortedMap.newBuilder[String, Command]
    b ++= commands.map(c => c.name -> c)
    b.result()
  }
}
