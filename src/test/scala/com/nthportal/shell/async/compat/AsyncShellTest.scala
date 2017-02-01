package com.nthportal.shell
package async.compat

import java.util.Collections

import com.nthportal.shell.compat.impl.TestParser
import com.nthportal.shell.compat.{Command, Shell => JShell}
import com.nthportal.shell.impl.NoOpOutputProvider

import scala.concurrent.Await
import scala.concurrent.duration._

class AsyncShellTest extends SimpleSpec {

  behavior of s"${classOf[AsyncShell].getSimpleName} (Java)"

  it should "execute inputs properly" in {
    val ic = new SimpleInputChannel
    val shell = AsyncShell.create(ic, JShell.create(TestParser, NoOpOutputProvider, Collections.emptyList[Command]()))

    shell.status0().isCompleted should be (false)
    val tc = shell.tabCompletion("")
    ic.sendAction(tc)
    val noOp = shell.inputAction(_ => Unit)
    ic.sendAction(noOp)

    Await.ready(noOp.future, Duration.Inf)

    val terminated = shell.terminate()
    terminated.toCompletableFuture.get()
    shell.status0().isCompleted should be (true)

    tc.future.isCompleted should be(true)
  }

  it should "only allow termination once" in {
    val ic = new SimpleInputChannel
    val shell = AsyncShell.create(ic, JShell.create(TestParser, NoOpOutputProvider, Collections.emptyList[Command]()))

    shell.terminate()

    an[IllegalStateException] should be thrownBy {shell.terminate()}
  }
}
