package com.nthportal.shell
package internal

import com.nthportal.shell.util.CommandTabCompleter

/**
  * A help [[Command command]] which is automatically generated by [[ShellCore]].
  *
  * @param shellCommands the commands for which to provide help messages
  */
private[internal] case class HelpCommand(shellCommands: ImmutableSeq[Command]) extends Command
                                                                                       with CommandTabCompleter {
  // This field must be declared before `commands` is sorted (during initialization)
  /**
    * @inheritdoc
    */
  override val name: String = ShellCore.helpCommandName

  /**
    * @inheritdoc
    */
  override protected final val commands = (this +: shellCommands).sortBy(_.name)

  /**
    * @inheritdoc
    */
  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = sink.writeln(getHelp(args))

  /**
    * @inheritdoc
    */
  override def description: Option[String] = Some("Shows the help information for a command")

  /**
    * @inheritdoc
    */
  override def help(args: ImmutableSeq[String]): Option[String] = Some(usage)

  override def equals(obj: Any): Boolean = obj match {
    case that: HelpCommand => commands.filterNot(_ eq this) == that.commands.filterNot(_ eq that)
    case _ => false
  }

  private def getHelp(args: ImmutableSeq[String]): String = {
    if (args.isEmpty) helpMessage
    else getHelpForCommand(args.head, args.tail)
  }

  private def getHelpForCommand(command: String, subArgs: ImmutableSeq[String]): String = {
    commandsByName.get(command)
      .map(fullHelp(_, subArgs))
      .getOrElse(s"No such command: $command")
  }

  private def fullHelp(command: Command, subArgs: ImmutableSeq[String]): String = {
    val descriptionStr = blankStringToNone(command.description)
    val helpStr = blankStringToNone(command.help(subArgs))

    if (descriptionStr.isEmpty) helpStr.getOrElse(noHelp(command, subArgs))
    else if (helpStr.isEmpty) formattedDescription(command)
    else s"${formattedDescription(command)}\n\n${helpStr.get}"
  }

  private def formattedDescription(command: Command): String = {
    s"${command.name} - \t${blankStringToNone(command.description).getOrElse("No description provided")}"
  }

  private def usage: String =
    s"Usage: $name\n" +
    s"       $name <command> [args...]"

  private def helpMessage: String = commands.map(formattedDescription).mkString("\n")

  private def noHelp(command: Command, subArgs: ImmutableSeq[String]): String = {
    s"No help for `${command.name}` with: ${subArgs.mkString(" ")}"
  }

  private def blankStringToNone(option: Option[String]): Option[String] = option match {
    case Some(s) if s.trim.isEmpty => None
    case o => o
  }
}