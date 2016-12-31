package com.nthportal.shell
package compat

import java.util
import java.util.Optional

import com.nthportal.shell.compat.{Command => JCommand, LineParser => JLineParser}
import com.nthportal.shell.{Command => SCommand, LineParser => SLineParser}

import scala.collection.JavaConverters
import scala.language.implicitConversions

object Converters {
  implicit def asJavaLineParser(parser: SLineParser): JLineParser = {
    (line: String) => JavaConverters.seqAsJavaList(parser.parseLine(line))
  }

  implicit def asScalaLineParser(parser: JLineParser): SLineParser = {
    (line: String) => listToScalaImmutableSeq(parser.parseLine(line))
  }

  implicit def asJavaOptional[T](option: Option[T]): Optional[T] = option match {
    case Some(t) => Optional.of(t)
    case None => Optional.empty()
  }

  implicit def asScalaOption[T](optional: Optional[T]): Option[T] = {
    if (optional.isPresent) Some(optional.get)
    else None
  }

  def asJavaCommand(command: SCommand): JCommand = new JCompatCommand(command)

  def asScalaCommand(command: JCommand): SCommand = new SCompatCommand(command)

  def listToScalaImmutableSeq[T](list: util.List[T]): ImmutableSeq[T] = {
    JavaConverters.asScalaBuffer(list).to[ImmutableSeq]
  }
}
