package com.nthportal.shell.async.compat

import java.util.concurrent.CompletionStage

import com.nthportal.shell.async.{InputAction => SInputAction}
import com.nthportal.shell.compat.{Shell => JShell}
import com.nthportal.shell.{Shell => SShell}

import scala.compat.java8.FutureConverters._
import scala.concurrent.Future

/**
  * An action requested to be executed by a [[JShell Shell]] by the
  * [[InputProvider input source]] of an [[AsyncShell]].
  *
  * @tparam T the type of the result of the action
  */
final class InputAction[T] private[compat](action: JShell => T)(implicit mapping: SShell => JShell) {
  private[compat] val underlying: SInputAction[T] = shell => action(mapping(shell))

  /**
    * Returns a [[CompletionStage]] which will contain the result of this action.
    *
    * @return a CompletionStage which will contain the result of this action
    */
  def completionStage: CompletionStage[T] = toJava(future)

  /**
    * Returns a [[Future]] which will contain the result of this action.
    *
    * @return a Future which will contain the result of this action
    */
  def future: Future[T] = underlying.future

  /**
    * Performs this action using a shell, and makes the result
    * available in [[future]]. Performs the action defined as [[action]].
    *
    * @param shell the shell with which to perform the action
    */
  private[compat] def doAction(shell: JShell): Unit = underlying.doAction(shell.underlying)
}
