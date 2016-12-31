package com.nthportal.shell
package async

import scala.concurrent.{Future, Promise}
import scala.util.Try

trait InputAction[T] {
  private val promise = Promise[T]

  def future: Future[T] = promise.future

  private[async] def doAction(implicit shell: Shell): Unit = promise.complete(Try(action(shell)))

  /**
    * Performs an action using a shell. After completing, the result of the
    * action will be available in [[future]].
    *
    * Note: This method should NOT be called from within [[AsyncShell]];
    * use [[doAction]] instead. (It is only accessible outside of the local
    * scope for use in Java-compatible classes.)
    *
    * @param shell the shell with which to perform the action
    * @return the result of the action
    */
  private[shell] def action(shell: Shell): T
}

object InputAction {
  def tabCompletion(line: String): InputAction[ImmutableSeq[String]] = _.tabComplete(line)

  def execution(line: String): InputAction[Unit] = _.executeLine(line)
}
