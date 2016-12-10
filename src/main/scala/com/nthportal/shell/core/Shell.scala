package com.nthportal.shell.core

import java.util.Objects

import com.google.common.base.CharMatcher
import com.nthportal.shell.core.builtin.HelpCommand
import com.nthportal.shell.util.{CommandExecutor, CommandTabCompleter}

class Shell private(commandsSeq: Seq[Command]) extends CommandTabCompleter
                                                                with CommandExecutor {
  val commands: Seq[Command] = HelpCommand(commandsSeq) +: commandsSeq

  override protected def noArgExecution: Option[String] = None

  override protected def noSuchCommandExecution(command: String, args: Seq[String]): Option[String] = {
    Some("Command not found: " + command)
  }
}

object Shell {
  def apply(commands: Seq[Command]): Shell = {
    validateCommands(commands)
    new Shell(commands)
  }

  @throws[IllegalArgumentException]
  @throws[NullPointerException]
  def validateCommands(commands: Seq[Command]): Unit = {
    // Check for null commands
    commands.foreach(Objects.requireNonNull)

    val names = commands.map(_.name)

    // Check for reserved help command name
    if (names.contains(helpCommandName)) {
      throw new IllegalArgumentException("'" + helpCommandName + "' is a reserved command name")
    }

    // Check for whitespace in commands
    if (!names.forall(CharMatcher.whitespace().matchesNoneOf(_))) {
      throw new IllegalArgumentException("Commands cannot contain whitespace")
    }

    // Check for duplicated command names
    val duplicates = names.toStream
      .groupBy(identity)
      .map {
        case (name, seq) => (name, seq.size)
      }
      .filter(_._2 > 1)
    if (duplicates.nonEmpty) {
      throw new IllegalArgumentException("Duplicated command names: " + duplicates.mkString(", "))
    }
  }

  private[core] val helpCommandName = "help"
}
