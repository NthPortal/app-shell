package com.nthportal.shell
package internal

import java.util.Objects

import com.google.common.base.CharMatcher
import com.nthportal.shell.util.{CommandExecutor, CommandTabCompleter}

private[shell] class ShellCore private(commandsSeq: ImmutableSeq[Command]) extends CommandTabCompleter
                                                                    with CommandExecutor {
  override final val commands: ImmutableIterable[Command] = (HelpCommand(commandsSeq) +: commandsSeq).sortBy(_.name)

  override protected def noArgExecution(implicit sink: OutputSink): Unit = {}

  override protected def noSuchCommandExecution(command: String, args: ImmutableSeq[String])
                                               (implicit sink: OutputSink): Unit = {
    sink.writeln(s"Command not found: $command")
  }
}

private[shell] object ShellCore {
  def apply(commands: ImmutableSeq[Command]): ShellCore = {
    validateCommands(commands)
    new ShellCore(commands)
  }

  @throws[IllegalArgumentException]
  @throws[NullPointerException]
  private def validateCommands(commands: ImmutableSeq[Command]): Unit = {
    // Check for null commands
    commands.foreach(Objects.requireNonNull[Command])

    val names = commands.map(_.name)

    // Check for reserved help command name
    require(!names.contains(helpCommandName), s"'$helpCommandName' is a reserved command name")

    // Check for whitespace in commands
    require(names.forall(CharMatcher.whitespace().matchesNoneOf(_)), "Commands cannot contain whitespace")

    // Check for duplicated command names
    val duplicates = names
      .groupBy(identity)
      .mapValues(_.size)
      .filter(_._2 > 1)
      .keys
    require(duplicates.isEmpty, s"Duplicated command name(s): ${duplicates.mkString(", ")}")
  }

  private[internal] val helpCommandName = "help"
}
