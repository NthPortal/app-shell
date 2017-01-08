package com.nthportal.shell.compat

import java.util

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.{OutputProvider, LineParser => SLineParser, Shell => SShell}

import scala.collection.JavaConverters

final class Shell private(private[compat] val underlying: SShell) {
  def commands: java.lang.Iterable[Command] = JavaConverters.asJavaIterable(underlying.commands.map(asJavaCommand))

  def tabComplete(line: String): util.List[String] = JavaConverters.seqAsJavaList(underlying.tabComplete(line))

  def executeLine(line: String): Unit = underlying.executeLine(line)
}

object Shell {
  def create(lineParser: LineParser, outputProvider: OutputProvider, commands: util.List[Command]): Shell = {
    create0(lineParser, outputProvider, commands)
  }

  def create(lineParser: com.nthportal.shell.LineParser,
             outputProvider: OutputProvider,
             commands: util.List[Command]): Shell = {
    create0(lineParser, outputProvider, commands)
  }

  private def create0(lineParser: SLineParser, outputProvider: OutputProvider, commands: util.List[Command]) = {
    new Shell(SShell(lineParser, outputProvider, listToScalaImmutableSeq(commands).map(asScalaCommand)))
  }
}
