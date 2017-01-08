package com.nthportal.shell
package async

import com.nthportal.shell.impl.NoOpOutputProvider
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class AsyncShellTest extends SimpleSpec {

  behavior of nameOf[AsyncShell]

  it should "execute inputs properly" in {
    val ic = SimpleInputChannel()
    val shell = AsyncShell(ic, Shell(WhitespaceDelineatingParser, NoOpOutputProvider))

    val tc = InputAction.tabCompletion("")
    ic.sendAction(tc)
    val terminated = shell.terminate()
    Await.result(terminated, Duration.Inf)

    tc.future.isCompleted should be(true)
  }

  it should "only allow termination once" in {
    val shell = AsyncShell(SimpleInputChannel(), Shell(WhitespaceDelineatingParser, NoOpOutputProvider))

    shell.terminate()

    an[IllegalStateException] should be thrownBy {shell.terminate()}
  }
}
