package com.nthportal
package shell
package util

/**
  * Something which delegates some behavior to one of a sequence of
  * [[Command commands]] (based on their names).
  */
trait CommandDelegator {
  /**
    * Returns a sequence of commands to which some behavior might be delegated.
    *
    * @return a sequence of commands to which some behavior might be delegated
    */
  protected def commands: ImmutableIterable[Command]

  // Field must be lazy so that `commands` is not null when this field is initialized.
  /**
    * A mapping of each command's name to itself.
    */
  protected lazy final val commandsByName: Map[String, Command] = commands.map(c => c.name -> c).toSortedMap
}
