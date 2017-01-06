package com.nthportal.shell.async

import scala.concurrent.Future

/**
  * Something which provides input (asynchronously) for an [[AsyncShell]].
  */
trait InputProvider {
  /**
    * Returns a [[Future]] which will contain the next [[InputAction action]]
    * to be executed (by an [[AsyncShell]]).
    *
    * Successive invocations of this method MUST NOT return Futures which will
    * be completed with the same action; they MUST return Futures which will be
    * completed with successive requested actions.
    *
    * @return a Future which will contain the next action to be executed
    */
  def nextAction: Future[InputAction[_]]
}
