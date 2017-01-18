package com.nthportal.shell
package compat

import java.util

import com.nthportal.shell.compat.{Command => JCommand, LineParser => JLineParser}
import com.nthportal.shell.{Command => SCommand, LineParser => SLineParser}

import scala.collection.JavaConverters._

/**
  * Converters between classes in Java and Scala APIs.
  */
object Converters {
  /**
    * Converts a [[SLineParser Scala line parser]] to a [[JLineParser Java line parser]].
    *
    * @param parser the Scala line parser to convert
    * @return a Java line parser
    */
  def asJavaLineParser(parser: SLineParser): JLineParser = parser match {
    case SCompatLineParser(p) => p
    case _ => JCompatLineParser(parser)
  }

  /**
    * Converts a [[JLineParser Java line parser]] to a [[SLineParser Scala line parser]].
    *
    * @param parser the Java line parser to convert
    * @return a Scala line parser
    */
  def asScalaLineParser(parser: JLineParser): SLineParser = parser match {
    case JCompatLineParser(p) => p
    case _ => SCompatLineParser(parser)
  }

  /**
    * Converts a [[SCommand Scala command]] to a [[JCommand Java command]].
    *
    * @param command the Scala command to convert
    * @return a Java command
    */
  def asJavaCommand(command: SCommand): JCommand = command match {
    case SCompatCommand(c) => c
    case _ => JCompatCommand(command)
  }

  /**
    * Converts a [[JCommand Java command]] to a [[SCommand Scala command]].
    *
    * @param command the Java command to convert
    * @return a Scala command
    */
  def asScalaCommand(command: JCommand): SCommand = command match {
    case JCompatCommand(c) => c
    case _ => SCompatCommand(command)
  }

  /**
    * Converts a [[util.List Java list]] to a
    * [[scala.collection.immutable.Seq Scala immutable sequence]].
    *
    * @param list the Java list to convert
    * @return a Scala immutable sequence
    */
  def listToScalaImmutableSeq[T](list: util.List[T]): ImmutableSeq[T] = asScalaBuffer(list).to[ImmutableSeq]
}
