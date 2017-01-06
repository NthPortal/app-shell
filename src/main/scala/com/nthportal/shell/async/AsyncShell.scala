package com.nthportal.shell
package async

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
  * An asynchronous shell. It processes commands and tab-completions asynchronously.
  *
  * @param inputProvider the [[InputProvider]] for this asynchronous shell
  * @param shell the (synchronous) shell to be managed asynchronously by this
  * @param ec the [[ExecutionContext]] in which to execute this asynchronous shell
  */
final class AsyncShell private(inputProvider: InputProvider)(implicit shell: Shell, ec: ExecutionContext) {
  @volatile
  private var terminated: Boolean = false
  private val termination = Promise[Unit]

  private def run(): Unit = {
    if (terminated) termination.success(Unit)
    else handleNextAction().onComplete(_ => run())
  }

  private def handleNextAction(): Future[Unit] = for (action <- inputProvider.nextAction) yield action.doAction

  /**
    * Terminates this asynchronous shell so that it will no longer process inputs.
    *
    * @return a Future which will be completed once the shell is fully terminated
    */
  def terminate(): Future[Unit] = {
    if (terminated) throw new IllegalStateException("Shell already terminated")
    terminated = true
    termination.future
  }
}

object AsyncShell {
  /**
    * Creates an asynchronous shell.
    *
    * @param inputProvider the [[InputProvider]] for the asynchronous shell
    * @param shell the (synchronous) shell to be managed asynchronously by the AsyncShell
    * @return an asynchronous shell created with the given parameters
    */
  def apply(inputProvider: InputProvider, shell: Shell): AsyncShell = apply(inputProvider)(shell, defaultContext)

  private[shell] def apply(inputProvider: InputProvider)(implicit shell: Shell, ec: ExecutionContext): AsyncShell = {
    val asyncShell = new AsyncShell(inputProvider)
    asyncShell.run()
    asyncShell
  }

  private[shell] def defaultContext: ExecutionContext = {
    ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())
  }
}
