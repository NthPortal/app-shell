package com.nthportal.shell
package compat

import java.util
import java.util.Optional

import com.nthportal.shell.compat.{Command => JCommand, LineParser => JLineParser}
import com.nthportal.shell.{Command => SCommand, LineParser => SLineParser}

import scala.collection.JavaConverters
import scala.language.implicitConversions

object Converters {
  implicit def asJavaLineParser(parser: SLineParser): JLineParser = parser match {
    case p: SCompatLineParser => p.parser
    case _ => new JCompatLineParser(parser)
  }

  implicit def asScalaLineParser(parser: JLineParser): SLineParser = parser match {
    case p: JCompatLineParser => p.parser
    case _ => new SCompatLineParser(parser)
  }

  implicit def asJavaOptional[T](option: Option[T]): Optional[T] = option match {
    case Some(t) => Optional.of(t)
    case None => Optional.empty()
  }

  implicit def asScalaOption[T](optional: Optional[T]): Option[T] = {
    if (optional.isPresent) Some(optional.get)
    else None
  }

  def asJavaCommand(command: SCommand): JCommand = command match {
    case c: SCompatCommand => c.command
    case _ => new JCompatCommand(command)
  }

  def asScalaCommand(command: JCommand): SCommand = command match {
    case c: JCompatCommand => c.command
    case _ => new SCompatCommand(command)
  }

  def listToScalaImmutableSeq[T](list: util.List[T]): ImmutableSeq[T] = {
    JavaConverters.asScalaBuffer(list).to[ImmutableSeq]
  }
}
