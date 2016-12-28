package com.nthportal.shell
package async

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future, Promise}

final class AsyncShell private(inputProvider: InputProvider)(implicit shell: Shell) {
  @volatile
  private var terminated: Boolean = false
  private val termination = Promise[Unit]
  implicit private val ec = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  private def run(): Unit = {
    if (terminated) termination.success(Unit)
    else handleNextAction().onComplete(_ => run())
  }

  private def handleNextAction(): Future[Unit] = for (action <- inputProvider.nextAction) yield action.doAction

  def terminate(): Future[Unit] = {
    if (terminated) throw new IllegalStateException("Shell already terminated")
    terminated = true
    termination.future
  }
}

object AsyncShell {
  def apply(inputProvider: InputProvider, shell: Shell): AsyncShell = {
    val asyncShell = new AsyncShell(inputProvider)(shell)
    asyncShell.run()
    asyncShell
  }
}
