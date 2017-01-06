package com.nthportal.shell
package async

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.control.NoStackTrace
import scala.util.{Failure, Success}

/**
  * An asynchronous shell. It processes commands and tab-completions asynchronously.
  *
  * @param inputProvider the [[InputProvider]] for this asynchronous shell
  * @param shell         the (synchronous) shell to be managed asynchronously by this
  * @param ec            the [[ExecutionContext]] in which to execute this asynchronous shell
  */
final class AsyncShell private(inputProvider: InputProvider)(implicit shell: Shell, ec: ExecutionContext) {

  import AsyncShell._

  private val cip = new CancellableInputProvider(inputProvider)
  private val termination = Promise[Unit]

  private def run(): Unit = {
    handleNextAction().onComplete {
      case Success(_) => run()
      case Failure(t) => t match {
        case TerminationException => termination.success(Unit)
        case t2 => termination.failure(t2)
      }
    }
  }

  private def handleNextAction(): Future[Unit] = for (action <- cip.nextAction) yield action.doAction

  /**
    * Terminates this asynchronous shell so that it will no longer process inputs.
    * It cannot be terminated more than once.
    *
    * Note: Invoking this method does NOT interrupt an action which is already
    * being processed.
    *
    * @return a Future which will be completed once the shell is fully terminated
    */
  def terminate(): Future[Unit] = {
    if (!cip.tryCancel()) throw new IllegalStateException("Shell already terminated")
    termination.future
  }
}

object AsyncShell {
  /**
    * Creates an asynchronous shell.
    *
    * @param inputProvider the [[InputProvider]] for the asynchronous shell
    * @param shell         the (synchronous) shell to be managed asynchronously by the AsyncShell
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

  private object TerminationException extends Exception("Shell terminated properly") with NoStackTrace

  /**
    * NOTE: Implementation requires that [[nextAction]] is not invoked again before the
    * result of the previous invocation has completed.
    */
  private class CancellableInputProvider(inputProvider: InputProvider) extends InputProvider {
    private val tuple: AtomicReference[(Boolean, Promise[InputAction[_]])] =
      new AtomicReference((false, Promise.successful[InputAction[_]](_ => Unit)))

    /**
      * Attempts to cancel/halt this [[InputProvider]].
      *
      * Returns `true` if it was canceled successfully; `false` if it was already canceled.
      *
      * @return true if it was canceled successfully; false if it was already canceled
      */
    def tryCancel(): Boolean = {
      val (cancelled, p) = tuple.getAndUpdate({ case (_, promise) => (true, promise) })
      if (!cancelled) p.tryFailure(TerminationException)
      !cancelled
    }

    override def nextAction: Future[InputAction[_]] = {
      val p = Promise[InputAction[_]]()
      val (cancelled, old) = tuple.getAndUpdate({ case (c, _) => (c, p) })

      if (!old.isCompleted) throw new IllegalStateException("Previous action is not completed")

      if (!cancelled) {
        p.tryCompleteWith(inputProvider.nextAction)
        p.future
      } else Future.failed(TerminationException)
    }
  }

}
