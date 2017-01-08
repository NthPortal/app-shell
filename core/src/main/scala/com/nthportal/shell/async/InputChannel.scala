package com.nthportal.shell.async

import scala.concurrent.Future

/**
  * Something which transmits input between a source and an [[AsyncShell]].
  */
trait InputChannel extends InputProvider {
  /**
    * Sends an action to be executed asynchronously by an [[AsyncShell]].
    *
    * Returns a [[Future]] which will contain the result of the action.
    * The Future returned SHALL be equivalent to the one returned by
    * invoking the [[InputAction.future future]] method of the given
    * action.
    *
    * @param action the action to be executed
    * @tparam T the type of the result of the action to be executed
    * @return a Future which will contain the result of the action
    */
  def sendAction[T](action: InputAction[T]): Future[T]
}
