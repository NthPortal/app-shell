package com.nthportal.shell
package async

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class InputActionTest extends SimpleSpec {

  behavior of classOf[InputAction[_]].getSimpleName

  it should "execute an execution properly" in {
    val os = StatefulOutputProvider()
    val shell = Shell(WhitespaceDelineatingParser, os)

    val ex = InputAction.execution("not-a-command")
    val f = ex.future
    f.isCompleted should be(false)
    os.writtenTo should be(false)

    ex.doAction(shell)
    f.isCompleted should be(true)
    os.writtenTo should be(true)
  }

  it should "execute a tab-completion properly" in {
    val t = TestCommand()
    val shell = Shell(WhitespaceDelineatingParser, StatefulOutputProvider(), t)

    val tc = InputAction.tabCompletion(t.name)
    val f = tc.future
    f.isCompleted should be(false)

    tc.doAction(shell)
    f.isCompleted should be(true)
    Await.result(f, Duration.Zero) should contain(t.name)
  }
}
