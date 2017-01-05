package com.nthportal.shell

/**
  * A command to be executed by a [[Shell]].
  */
trait Command extends SubCommand {
  /**
    * The name of the command.
    */
  val name: String
}
