package com.nthportal.shell
package compat

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Command => JCommand}
import com.nthportal.shell.{Command => SCommand}

import scala.collection.JavaConverters._
import scala.compat.java8.OptionConverters._

private[compat] class SCompatCommand(val command: JCommand) extends SCommand {
  override val name: String = command.name

  override def description: Option[String] = toScala(command.description)

  override def help(args: ImmutableSeq[String]): Option[String] = toScala(command.help(seqAsJavaList(args)))

  override def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String] = {
    listToScalaImmutableSeq(command.tabComplete(seqAsJavaList(args)))
  }

  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = {
    command.execute(seqAsJavaList(args), sink)
  }
}
