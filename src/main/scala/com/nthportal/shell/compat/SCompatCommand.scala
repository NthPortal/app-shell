package com.nthportal.shell
package compat

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Command => JCommand}
import com.nthportal.shell.{Command => SCommand}

import scala.collection.JavaConverters

private[compat] class SCompatCommand(val command: JCommand) extends SCommand {
  override val name: String = command.name

  override def description: Option[String] = command.description

  override def help(args: ImmutableSeq[String]): Option[String] = command.help(JavaConverters.seqAsJavaList(args))

  override def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = {
    listToScalaImmutableSeq(command.tabComplete(JavaConverters.seqAsJavaList(args)))
  }

  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = {
    command.execute(JavaConverters.seqAsJavaList(args), sink)
  }
}
