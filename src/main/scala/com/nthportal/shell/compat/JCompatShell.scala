package com.nthportal.shell
package compat

import java.util

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Shell => JShell, LineParser => JLineParser}
import com.nthportal.shell.{Shell => SShell, LineParser => SLineParser}

import scala.collection.JavaConverters

private[compat] class JCompatShell(shell: SShell) extends JShell {
  override def commands(): util.List[Command] = JavaConverters.seqAsJavaList(shell.commands.map(asJavaCommand))

  override def lineParser(): LineParser = shell.lineParser

  override def tabComplete(line: String): util.List[String] = JavaConverters.seqAsJavaList(shell.tabComplete(line))

  override def executeLine(line: String): Unit = shell.executeLine(line)
}

private[compat] object JCompatShell {
  def create(lineParser: JLineParser, commands: util.List[Command], outputProvider: OutputProvider): JShell = {
    create0(lineParser, commands, outputProvider)
  }

  def create0(lineParser: SLineParser, commands: util.List[Command], outputProvider: OutputProvider): JShell = {
    new JCompatShell(SShell(lineParser, listToScalaImmutableSeq(commands).map(asScalaCommand), outputProvider))
  }
}
