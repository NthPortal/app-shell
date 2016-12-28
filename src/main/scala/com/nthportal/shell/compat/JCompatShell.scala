package com.nthportal.shell
package compat

import java.util

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Shell => JShell}
import com.nthportal.shell.{Shell => SShell}

import scala.collection.JavaConverters

private[compat] class JCompatShell(shell: SShell) extends JShell {
  override def commands(): util.List[Command] = JavaConverters.seqAsJavaList(shell.commands.map(asJavaCommand))

  override def lineParser(): LineParser = shell.lineParser

  override def tabComplete(line: String): util.List[String] = JavaConverters.seqAsJavaList(shell.tabComplete(line))

  override def executeLine(line: String): Unit = shell.executeLine(line)
}

private[compat] object JCompatShell {
  def apply(lineParser: LineParser, commands: util.List[Command], outputProvider: OutputProvider): JCompatShell = {
    new JCompatShell(SShell(lineParser, listToScalaImmutableSeq(commands).map(asScalaCommand), outputProvider))
  }
}
