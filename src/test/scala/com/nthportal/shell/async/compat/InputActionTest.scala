package com.nthportal.shell.async.compat

import java.util
import java.util.Collections
import java.util.concurrent.TimeUnit
import java.util.function.Function

import com.nthportal.shell.SimpleSpec
import com.nthportal.shell.compat.impl.TestCommand
import com.nthportal.shell.compat.{Command, Shell}
import com.nthportal.shell.impl.StatefulOutputProvider
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.control.NoStackTrace

class InputActionTest extends SimpleSpec {

  behavior of s"${classOf[InputAction[_]].getSimpleName} (Java)"

  it should "execute an execution properly" in {
    val os = StatefulOutputProvider()
    val shell = Shell.create(WhitespaceDelineatingParser, os, Collections.emptyList[Command]())
    val iac = BasicInputActionCreator(shell)

    val ex = iac.execution("not-a-command")
    val f = ex.future
    f.isCompleted should be(false)
    os.writtenTo should be(false)

    ex.doAction(shell)
    f.isCompleted should be(true)
    os.writtenTo should be(true)
  }

  it should "execute a tab-completion properly" in {
    val t = TestCommand()
    val shell = Shell.create(WhitespaceDelineatingParser, StatefulOutputProvider(), util.Arrays.asList[Command](t))
    val iac = BasicInputActionCreator(shell)

    val tc = iac.tabCompletion(t.name)
    val f = tc.future
    f.isCompleted should be(false)

    tc.doAction(shell)
    f.isCompleted should be(true)
    tc.completionStage.toCompletableFuture.get(0, TimeUnit.SECONDS) should contain(t.name)
  }

  it should "return a failed Future if the action throws an exception" in {
    val shell = Shell.create(WhitespaceDelineatingParser, StatefulOutputProvider(), Collections.emptyList[Command]())
    val iac = BasicInputActionCreator(shell)

    val ex = new Exception with NoStackTrace
    val action: InputAction[Unit] = iac.inputAction(_ => throw ex)
    val f = action.future

    action.doAction(shell)
    f.isCompleted should be(true)
    Await.result(f.failed, Duration.Zero) should be(ex)
  }
}
