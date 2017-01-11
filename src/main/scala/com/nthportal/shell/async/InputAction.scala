package com.nthportal.shell
package async

import scala.concurrent.{Future, Promise}
import scala.util.Try

/**
  * An action requested to be executed by a [[Shell]] by the
  * [[InputProvider input source]] of an [[AsyncShell]].
  *
  * @tparam T the type of the result of the action
  */
trait InputAction[T] {
  private val promise = Promise[T]

  /**
    * Returns a [[Future]] which will contain the result of this action.
    *
    * @return a Future which will contain the result of this action
    */
  def future: Future[T] = promise.future

  /**
    * Performs this action using a shell, and makes the result
    * available in [[future]]. Performs the action defined as [[action]].
    *
    * @param shell the shell with which to perform the action
    */
  private[async] def doAction(implicit shell: Shell): Unit = promise.complete(Try(action(shell)))

  /**
    * Returns the result of an action performed with a [[Shell]].
    *
    * @param shell the shell with which to perform the action
    * @return the result of the action
    */
  private[InputAction] def action(shell: Shell): T
}

object InputAction {
  /**
    * Creates an input action for tab-completing a line.
    *
    * @param line the line to be tab-completed
    * @return an input action for tab-completing the given line
    * @see [[Shell.tabComplete]]
    */
  def tabCompletion(line: String): InputAction[ImmutableSeq[String]] = _.tabComplete(line)

  /**
    * Creates an input action for executing a line.
    *
    * @param line the line to be executed
    * @return an input action for executing the given line
    * @see [[Shell.executeLine]]
    */
  def execution(line: String): InputAction[Unit] = _.executeLine(line)
}
