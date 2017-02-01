package com.nthportal.shell
package async

import com.nthportal.shell.async.AsyncShell.CancellableInputProvider
import com.nthportal.shell.impl.NoOpOutputProvider
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class AsyncShellTest extends SimpleSpec {

  behavior of classOf[AsyncShell].getSimpleName

  it should "execute inputs properly" in {
    val ic = SimpleInputChannel()
    val shell = AsyncShell(ic, Shell(WhitespaceDelineatingParser, NoOpOutputProvider))
    shell.status.isCompleted should be (false)

    val tc = InputAction.tabCompletion("")
    ic.sendAction(tc)
    val terminated = shell.terminate()
    Await.ready(terminated, Duration.Inf)

    tc.future.isCompleted should be(true)
    shell.status.isCompleted should be (true)
  }

  it should "only allow termination once" in {
    val shell = AsyncShell(SimpleInputChannel(), Shell(WhitespaceDelineatingParser, NoOpOutputProvider))

    shell.terminate()

    an[IllegalStateException] should be thrownBy {shell.terminate()}
  }

  behavior of classOf[AsyncShell.CancellableInputProvider].getSimpleName

  it should "not allow `nextAction` to be invoked if the result of the previous invocation is not completed" in {
    val cip = new CancellableInputProvider(SimpleInputChannel())
    cip.nextAction

    an[IllegalStateException] should be thrownBy {cip.nextAction}
  }

  it should "fail `nextAction` if it has been cancelled" in {
    val cip = new CancellableInputProvider(SimpleInputChannel())
    cip.tryCancel()

    val f = cip.nextAction
    f.isCompleted should be(true)
    Await.result(f.failed, Duration.Zero) should be(AsyncShell.TerminationException)
  }
}
