package com.nthportal.shell.async

import com.nthportal.collection.concurrent.FutureQueue

import scala.concurrent.Future

/**
  * A simple [[InputChannel]] implementation.
  */
final class SimpleInputChannel extends InputChannel {
  private val queue = FutureQueue[InputAction[_]]()

  override def sendAction[T](action: InputAction[T]): Future[T] = {
    queue += action
    action.future
  }

  override def nextAction: Future[InputAction[_]] = queue.dequeue()
}

object SimpleInputChannel {
  def apply(): SimpleInputChannel = new SimpleInputChannel()
}
