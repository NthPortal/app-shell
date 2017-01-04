package com.nthportal.shell

import com.nthportal.shell.internal.ShellCore

sealed trait Shell {
  def commands: ImmutableIterable[Command]

  def lineParser: LineParser

  def tabComplete(line: String): ImmutableSeq[String]

  def executeLine(line: String): Unit
}

object Shell {
  def apply(lineParser: LineParser,
            commands: ImmutableSeq[Command],
            outputProvider: OutputProvider): Shell = {
    Impl(ShellCore(commands), lineParser, outputProvider)
  }

  private case class Impl(core: ShellCore,
                          lineParser: LineParser,
                          outputProvider: OutputProvider) extends Shell {
    implicit private def sink: OutputSink = outputProvider

    override def commands: ImmutableIterable[Command] = core.commands

    override def tabComplete(line: String): ImmutableSeq[String] = {
      core.tabComplete(lineParser.parseLineForTabCompletion(line))
    }

    override def executeLine(line: String): Unit = core.execute(lineParser.parseLineForExecution(line))
  }
}
