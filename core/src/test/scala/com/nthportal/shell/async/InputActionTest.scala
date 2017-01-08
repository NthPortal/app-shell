package com.nthportal.shell
package async

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.control.NoStackTrace

class InputActionTest extends SimpleSpec {

  behavior of nameOf[InputAction[_]]

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

  it should "return a failed Future if the action throws an exception" in {
    val shell = Shell(WhitespaceDelineatingParser, StatefulOutputProvider())

    val ex = new Exception with NoStackTrace
    val action: InputAction[Unit] = _ => throw ex
    val f = action.future

    action.doAction(shell)
    f.isCompleted should be(true)
    Await.result(f.failed, Duration.Zero) should be(ex)
  }
}
