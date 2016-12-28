package com.nthportal.shell
package async

import scala.concurrent.{Future, Promise}
import scala.util.Try

sealed trait InputAction[T] {
  private val promise = Promise[T]

  def future: Future[T] = promise.future

  private[async] def doAction(implicit shell: Shell): Unit = promise.complete(Try(action))

  private[async] def action(implicit shell: Shell): T
}

case class TabCompletion(line: String) extends InputAction[ImmutableSeq[String]] {
  override private[async] def action(implicit shell: Shell) = shell.tabComplete(line)
}

case class Execution(line: String) extends InputAction[Unit] {
  override private[async] def action(implicit shell: Shell) = shell.executeLine(line)
}
