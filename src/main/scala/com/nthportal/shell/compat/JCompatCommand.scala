package com.nthportal.shell
package compat

import java.util
import java.util.Optional

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Command => JCommand}
import com.nthportal.shell.{Command => SCommand}

import scala.collection.JavaConverters

private[compat] class JCompatCommand(command: SCommand) extends JCommand {
  override def name(): String = command.name

  override def description(): Optional[String] = command.description

  override def help(args: util.List[String]): Optional[String] = command.help(listToScalaImmutableSeq(args))

  override def tabComplete(args: util.List[String]): util.List[String] = {
    JavaConverters.seqAsJavaList(command.tabComplete(listToScalaImmutableSeq(args)))
  }

  override def execute(args: util.List[String], sink: OutputSink): Unit = {
    command.execute(listToScalaImmutableSeq(args))(sink)
  }
}
