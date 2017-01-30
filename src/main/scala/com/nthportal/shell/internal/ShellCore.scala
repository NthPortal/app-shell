package com.nthportal.shell
package internal

import java.util.Objects

import com.google.common.base.CharMatcher
import com.nthportal.shell.internal.util.DuplicateChecker
import com.nthportal.shell.util.{CommandExecutor, CommandTabCompleter}

/**
  * The main part of a [[Shell]] which executes and tab-completes commands.
  *
  * @param commandsSeq the commands to use for execution and tab-completion
  */
private[shell] class ShellCore private(commandsSeq: ImmutableSeq[Command]) extends CommandTabCompleter
                                                                    with CommandExecutor {
  /**
    * @inheritdoc
    */
  override final val commands: ImmutableIterable[Command] = (HelpCommand(commandsSeq) +: commandsSeq).sortBy(_.name)

  /**
    * @inheritdoc
    */
  override protected def noArgExecution(implicit sink: OutputSink): Unit = {}

  /**
    * @inheritdoc
    */
  override protected def noSuchCommandExecution(command: String, args: ImmutableSeq[String])
                                               (implicit sink: OutputSink): Unit = {
    sink.writeln(s"Command not found: $command")
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: ShellCore => commands == that.commands
    case _ => false
  }
}

private[shell] object ShellCore {
  /**
    * Creates a new shell core.
    *
    * @param commands the commands with which to create the shell core
    * @return a new shell core with the given commands
    */
  def apply(commands: ImmutableSeq[Command]): ShellCore = {
    validateCommands(commands)
    new ShellCore(commands)
  }

  /**
    * Checks that all commands in a sequence are valid.
    *
    * @param commands the commands to check
    *
    * @throws IllegalArgumentException if one or more commands are invalid
    * @throws NullPointerException if any command is `null`
    */
  @throws[IllegalArgumentException]
  @throws[NullPointerException]
  private def validateCommands(commands: ImmutableSeq[Command]): Unit = {
    // Check for null commands
    commands.foreach(Objects.requireNonNull[Command])

    val names = commands.map(_.name)

    // Check for reserved help command name
    require(!names.contains(helpCommandName), s"'$helpCommandName' is a reserved command name")

    // Check for whitespace in commands
    require(names.forall(CharMatcher.whitespace().matchesNoneOf), "Commands cannot contain whitespace")

    // Check for duplicated command names
    DuplicateChecker.check(names, "command name")
  }

  private[internal] val helpCommandName = "help"
}
