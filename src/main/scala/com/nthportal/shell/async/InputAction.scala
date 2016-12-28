package com.nthportal.shell
package async

import scala.concurrent.{Future, Promise}
import scala.util.Try

trait InputAction[T] {
  private val promise = Promise[T]

  def future: Future[T] = promise.future

  private[async] def doAction(implicit shell: Shell): Unit = promise.complete(Try(action(shell)))

  private[InputAction] def action(shell: Shell): T
}

object InputAction {
  def tabCompletion(line: String): InputAction[ImmutableSeq[String]] = _.tabComplete(line)

  def execution(line: String): InputAction[Unit] = _.executeLine(line)
}
