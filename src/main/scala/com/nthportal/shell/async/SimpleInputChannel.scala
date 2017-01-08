package com.nthportal.shell.async

import java.util.concurrent.atomic.AtomicReference

import com.nthportal.shell.async.SimpleInputChannel.ActionQueue

import scala.collection.immutable.Queue
import scala.concurrent.{Future, Promise}

/**
  * A simple [[InputChannel]] implementation.
  */
final class SimpleInputChannel extends InputChannel {
  private val atomic = new AtomicReference(ActionQueue(Queue.empty, Queue.empty))

  /**
    * @inheritdoc
    */
  override def sendAction[T](action: InputAction[T]): Future[T] = {
    val queue = atomic.getAndUpdate(q => {
      if (q.promises.nonEmpty) q.copy(promises = q.promises.tail)
      else q.copy(actions = q.actions :+ action)
    })

    if (queue.promises.nonEmpty) {
      queue.promises.head.success(action)
    }

    action.future
  }

  /**
    * @inheritdoc
    */
  override def nextAction: Future[InputAction[_]] = {
    val p = Promise[InputAction[_]]

    val queue = atomic.getAndUpdate(q => {
      if (q.actions.nonEmpty) q.copy(actions = q.actions.tail)
      else q.copy(promises = q.promises :+ p)
    })

    if (queue.actions.nonEmpty) {
      p.success(queue.actions.head)
    }

    p.future
  }
}

object SimpleInputChannel {
  def apply(): SimpleInputChannel = new SimpleInputChannel()

  private case class ActionQueue(promises: Queue[Promise[InputAction[_]]], actions: Queue[InputAction[_]])

}
