package com.nthportal.shell.compat

import java.util

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.{OutputProvider, LineParser => SLineParser, Shell => SShell}

import scala.collection.JavaConverters

final class Shell private(private[compat] val underlying: SShell) {
  def commands: util.List[Command] = JavaConverters.seqAsJavaList(underlying.commands.map(asJavaCommand))

  def lineParser: LineParser = underlying.lineParser

  def tabComplete(line: String): util.List[String] = JavaConverters.seqAsJavaList(underlying.tabComplete(line))

  def executeLine(line: String): Unit = underlying.executeLine(line)
}

object Shell {
  def create(lineParser: LineParser, commands: util.List[Command], outputProvider: OutputProvider): Shell = {
    create0(lineParser, commands, outputProvider)
  }

  def create(lineParser: com.nthportal.shell.LineParser,
             commands: util.List[Command],
             outputProvider: OutputProvider): Shell = {
    create0(lineParser, commands, outputProvider)
  }

  private def create0(lineParser: SLineParser,
                      commands: util.List[Command],
                      outputProvider: OutputProvider): Shell = {
    new Shell(SShell(lineParser, listToScalaImmutableSeq(commands).map(asScalaCommand), outputProvider))
  }
}
