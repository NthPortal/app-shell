package com.nthportal.shell

import com.nthportal.shell.internal.ShellCore

/**
  * A shell for executing commands.
  */
sealed trait Shell {
  /**
    * Returns the commands which this shell can execute.
    *
    * @return the commands which this shell can execute
    */
  def commands: ImmutableIterable[Command]

  /**
    * Returns the [[LineParser]] which this shell uses to parse lines.
    *
    * @return the LineParser which this shell uses to parse lines
    */
  def lineParser: LineParser

  /**
    * Returns a sequence of suggested completions for the final argument
    * of a line. Returns an empty sequence if no suggestions are available
    * for a line.
    *
    * @param line the line for which to generate suggested completions
    * @return a sequence of suggested completions for the line
    */
  def tabComplete(line: String): ImmutableSeq[String]

  /**
    * Executes a line.
    *
    * @param line the line to execute
    */
  def executeLine(line: String): Unit
}

object Shell {
  /**
    * Creates a shell.
    *
    * @param lineParser     the [[LineParser]] to use for the shell
    * @param outputProvider the [[OutputProvider]] for the shell
    * @param commands       the commands for the shell to execute
    * @return a shell created with the given parameters
    */
  def apply(lineParser: LineParser,
            outputProvider: OutputProvider,
            commands: ImmutableSeq[Command]): Shell = {
    Impl(ShellCore(commands), lineParser, outputProvider, Nil)
  }

  /**
    * Creates a shell.
    *
    * @param lineParser     the [[LineParser]] to use for the shell
    * @param outputProvider the [[OutputProvider]] for the shell
    * @param commands       the commands for the shell to execute
    * @return a shell created with the given parameters
    */
  def apply(lineParser: LineParser,
            outputProvider: OutputProvider,
            commands: Command*): Shell = {
    apply(lineParser, outputProvider, commands.to[ImmutableSeq])
  }

  /**
    * Creates a shell with [[LinePreProcessor line pre-processors]].
    *
    * @param lineParser     the [[LineParser]] to use for the shell
    * @param outputProvider the [[OutputProvider]] for the shell
    * @param preProcessors  [[LinePreProcessor]]s to use before
    *                       tab-completing or executing lines
    * @param commands       the commands for the shell to execute
    * @return a shell created with the given parameters
    */
  def apply(lineParser: LineParser,
            outputProvider: OutputProvider,
            preProcessors: ImmutableSeq[LinePreProcessor],
            commands: ImmutableSeq[Command]): Shell = {
    Impl(ShellCore(commands), lineParser, outputProvider, preProcessors)
  }

  /**
    * Creates a shell with [[LinePreProcessor line pre-processors]].
    *
    * @param lineParser     the [[LineParser]] to use for the shell
    * @param outputProvider the [[OutputProvider]] for the shell
    * @param preProcessors  [[LinePreProcessor]]s to use before
    *                       tab-completing or executing lines
    * @param commands       the commands for the shell to execute
    * @return a shell created with the given parameters
    */
  def apply(lineParser: LineParser,
            outputProvider: OutputProvider,
            preProcessors: ImmutableSeq[LinePreProcessor],
            commands: Command*): Shell = {
    apply(lineParser, outputProvider, preProcessors, commands.to[ImmutableSeq])
  }

  private case class Impl(core: ShellCore,
                          lineParser: LineParser,
                          outputProvider: OutputProvider,
                          preProcessors: ImmutableSeq[LinePreProcessor]) extends Shell {
    implicit private def sink: OutputSink = outputProvider

    override def commands: ImmutableIterable[Command] = core.commands

    private def preProcessLine(line: ImmutableSeq[String]): ImmutableSeq[String] = {
      preProcessors.foldLeft(line) { (seq, p) => p(seq, lineParser.parseLineForExecution) }
    }

    override def tabComplete(line: String): ImmutableSeq[String] = {
      val processed = preProcessLine(lineParser.parseLineForTabCompletion(line))
      core.tabComplete(processed)
    }

    override def executeLine(line: String): Unit = {
      val processed = preProcessLine(lineParser.parseLineForExecution(line))
      core.execute(processed)
    }
  }

}
