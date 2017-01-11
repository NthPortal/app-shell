package com.nthportal.shell
package compat

import java.util
import java.util.Optional

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.{Command => JCommand}
import com.nthportal.shell.{Command => SCommand}

import scala.collection.JavaConverters._
import scala.compat.java8.OptionConverters._

private[compat] case class JCompatCommand(command: SCommand) extends JCommand {
  override def name(): String = command.name

  override def description(): Optional[String] = toJava(command.description)

  override def help(args: util.List[String]): Optional[String] = toJava(command.help(listToScalaImmutableSeq(args)))

  override def tabComplete(args: util.List[String]): util.List[String] = {
    seqAsJavaList(command.tabComplete(listToScalaImmutableSeq(args)))
  }

  override def execute(args: util.List[String], sink: OutputSink): Unit = {
    command.execute(listToScalaImmutableSeq(args))(sink)
  }
}
