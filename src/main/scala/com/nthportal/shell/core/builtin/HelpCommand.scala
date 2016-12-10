package com.nthportal.shell
package core
package builtin

import com.nthportal.shell.util.CommandTabCompleter

private[core] case class HelpCommand(shellCommands: ImmutableSeq[Command]) extends Command
                                                                                   with CommandTabCompleter {
  override protected val commands = this +: shellCommands.sortBy(_.name)

  override val name: String = Shell.helpCommandName

  override def execute(args: ImmutableSeq[String]): Option[String] = help(args)

  override def description: Option[String] = Some("Shows the help information for a command")

  override def help(args: ImmutableSeq[String]): Option[String] = Some(getHelp(args))

  private def getHelp(args: ImmutableSeq[String]): String = {
    if (args.isEmpty) helpMessage
    else getHelpForCommand(args.head, args.tail)
  }

  private def getHelpForCommand(command: String, args: ImmutableSeq[String]): String = {
    if (command == name) descriptionForCommand(this)
    else {
      commandsByName.get(command)
        .map(_.help(args.tail))
        .map(_.getOrElse(noHelp(args)))
        .map {
          case "" => noHelp(args)
          case s => s
        }
        .getOrElse(s"No such command: $command")
    }
  }

  private def descriptionForCommand(command: Command): String = {
     s"${command.name} - \t${command.description.getOrElse("No description provided")}"
  }

  private def helpMessage: String = commands.map(descriptionForCommand).mkString("\n")

  private def noHelp(args: ImmutableSeq[String]): String = s"No help for: ${args.mkString(" ")}"
}
