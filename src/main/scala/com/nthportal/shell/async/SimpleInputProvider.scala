package com.nthportal.shell.async

import akka.agent.Agent
import com.nthportal.shell.async.SimpleInputProvider.ActionQueue

import scala.collection.immutable.Queue
import scala.concurrent.{ExecutionContext, Future, Promise}

final class SimpleInputProvider(implicit ec: ExecutionContext = ExecutionContext.global) extends InputProvider {
  private val agent = Agent(ActionQueue(Queue.empty, Queue.empty))

  def appendAction(action: InputAction[_]): Unit = {
    agent.send(queue => {
      val promised = queue.promisedActions
      if (promised.nonEmpty) {
        promised.head.success(action)
        ActionQueue(promised.tail, queue.waitingActions)
      } else ActionQueue(promised, queue.waitingActions :+ action)
    })
  }

  override def nextAction: Future[InputAction[_]] = {
    val p = Promise[InputAction[_]]
    agent.send(queue => {
      val waiting = queue.waitingActions
      if (waiting.nonEmpty) {
        p.success(waiting.head)
        ActionQueue(queue.promisedActions, waiting.tail)
      } else ActionQueue(queue.promisedActions :+ p, waiting)
    })
    p.future
  }
}

private object SimpleInputProvider {
  case class ActionQueue(promisedActions: Queue[Promise[InputAction[_]]], waitingActions: Queue[InputAction[_]])
}
