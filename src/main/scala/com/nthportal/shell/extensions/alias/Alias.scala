package com.nthportal.shell
package extensions.alias

/**
  * An alias for a [[Shell]] input line.
  *
  * @param name      the name of this alias
  * @param expansion the value with which to replace this alias when
  *                  used in an input line
  */
case class Alias(name: String, expansion: String)

object Alias {
  /**
    * Creates an alias for a command (with no added arguments).
    *
    * @param name    the name of the alias
    * @param command the command for which it should be an alias
    * @return an alias for the specified command
    */
  def forCommand(name: String, command: Command): Alias = apply(name, command.name)
}
