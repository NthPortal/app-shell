package com.nthportal.shell.core
package builtin

import com.nthportal.shell.util.CommandTabCompleter

private[core] case class HelpCommand(shellCommands: Seq[Command]) extends Command with CommandTabCompleter {
  override protected val commands = this +: shellCommands.sortBy(_.name)

  override val name: String = Shell.helpCommandName

  override def execute(args: Seq[String]): Option[String] = help(args)

  override def description: Option[String] = Some("Shows the help information for a command")

  override def help(args: Seq[String]): Option[String] = Some(getHelp(args))

  private def getHelp(args: Seq[String]): String = {
    if (args.isEmpty) helpMessage
    else getHelpForCommand(args.head, args.tail)
  }

  private def getHelpForCommand(command: String, args: Seq[String]): String = {
    if (command == name) descriptionForCommand(this)
    else {
      commandsByName.get(command)
        .map(_.help(args.tail))
        .map(_.getOrElse(noHelp(args)))
        .map {
          case "" => noHelp(args)
          case s => s
        }
        .getOrElse("No such command: " + command)
    }
  }

  private def descriptionForCommand(command: Command): String = {
    command.name + " - \t" + command.description.getOrElse("No description provided")
  }

  private def helpMessage: String = commands.map(descriptionForCommand).mkString("\n")

  private def noHelp(args: Seq[String]): String = "No help for: " + args.mkString(" ")
}
