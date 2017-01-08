package com.nthportal.shell
package compat

import java.util

import com.nthportal.shell.compat.{Command => JCommand, LineParser => JLineParser}
import com.nthportal.shell.{Command => SCommand, LineParser => SLineParser}

import scala.collection.JavaConverters

/**
  * Converters between classes in Java and Scala APIs.
  */
object Converters {
  /**
    * Converts a [[JLineParser Java line parser]] to a [[SLineParser Scala line parser]].
    *
    * @param parser the Java line parser to convert
    * @return a Scala line parser
    */
  def asScalaLineParser(parser: JLineParser): SLineParser = new SCompatLineParser(parser)

  /**
    * Converts a [[SCommand Scala command]] to a [[JCommand Java command]].
    *
    * @param command the Scala command to convert
    * @return a Java command
    */
  def asJavaCommand(command: SCommand): JCommand = command match {
    case c: SCompatCommand => c.command
    case _ => new JCompatCommand(command)
  }

  /**
    * Converts a [[JCommand Java command]] to a [[SCommand Scala command]].
    *
    * @param command the Java command to convert
    * @return a Scala command
    */
  def asScalaCommand(command: JCommand): SCommand = command match {
    case c: JCompatCommand => c.command
    case _ => new SCompatCommand(command)
  }

  /**
    * Converts a [[util.List Java list]] to a
    * [[scala.collection.immutable.Seq Scala immutable sequence]].
    *
    * @param list the Java list to convert
    * @return a Scala immutable sequence
    */
  def listToScalaImmutableSeq[T](list: util.List[T]): ImmutableSeq[T] = {
    JavaConverters.asScalaBuffer(list).to[ImmutableSeq]
  }
}
