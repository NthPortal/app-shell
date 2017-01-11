package com.nthportal.shell.compat

import java.util

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.{OutputProvider, LineParser => SLineParser, Shell => SShell}

import scala.collection.JavaConverters

final class Shell private(private[compat] val underlying: SShell) {
  /**
    * Returns the commands which this shell can execute.
    *
    * @return the commands which this shell can execute
    */
  def commands: java.lang.Iterable[Command] = JavaConverters.asJavaIterable(underlying.commands.map(asJavaCommand))

  /**
    * Returns the [[LineParser]] which this shell uses to parse lines.
    *
    * @return the LineParser which this shell uses to parse lines
    */
  def lineParser: LineParser = asJavaLineParser(underlying.lineParser)

  /**
    * Returns a sequence of suggested completions for the final argument
    * of a line. Returns an empty sequence if no suggestions are available
    * for a line.
    *
    * @param line the line for which to generate suggested completions
    * @return a sequence of suggested completions for the line
    */
  def tabComplete(line: String): util.List[String] = JavaConverters.seqAsJavaList(underlying.tabComplete(line))

  /**
    * Executes a line.
    *
    * @param line the line to execute
    */
  def executeLine(line: String): Unit = underlying.executeLine(line)
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
  def create(lineParser: LineParser, outputProvider: OutputProvider, commands: util.List[Command]): Shell = {
    create0(asScalaLineParser(lineParser), outputProvider, commands)
  }

  /**
    * Creates a shell.
    *
    * @param lineParser     the [[SLineParser Scala line parser]] to use for the shell
    * @param outputProvider the [[OutputProvider]] for the shell
    * @param commands       the commands for the shell to execute
    * @return a shell created with the given parameters
    */
  def create(lineParser: com.nthportal.shell.LineParser,
             outputProvider: OutputProvider,
             commands: util.List[Command]): Shell = {
    create0(lineParser, outputProvider, commands)
  }

  private def create0(lineParser: SLineParser, outputProvider: OutputProvider, commands: util.List[Command]) = {
    new Shell(SShell(lineParser, outputProvider, listToScalaImmutableSeq(commands).map(asScalaCommand)))
  }
}
