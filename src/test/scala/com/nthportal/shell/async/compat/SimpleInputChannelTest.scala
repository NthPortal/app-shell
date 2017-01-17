package com.nthportal.shell
package async.compat

import java.util.Collections
import java.util.concurrent.CompletionStage

import com.nthportal.shell.compat.{Shell, Command}
import com.nthportal.shell.impl.NoOpOutputProvider
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.compat.java8.FutureConverters._
import scala.concurrent.Future
import scala.language.implicitConversions

class SimpleInputChannelTest extends SimpleSpec {
  import SimpleInputChannelTest._

  implicit private val iac = BasicInputActionCreator(
    Shell.create(WhitespaceDelineatingParser, NoOpOutputProvider, Collections.emptyList[Command]()))

  behavior of s"${classOf[SimpleInputChannel].getSimpleName} (Java)"

  it should "complete CompletionStages from `nextAction` after `sendAction` is invoked" in {
    val channel = new SimpleInputChannel()

    val next = channel.nextAction
    next.isCompleted should be(false)

    channel.sendNoOp()
    next.isCompleted should be(true)

    val next2 = channel.nextAction
    val next3 = channel.nextAction
    next2.isCompleted should be(false)
    next3.isCompleted should be(false)

    channel.sendNoOp()
    next2.isCompleted should be(true)
    next3.isCompleted should be(false)

    channel.sendNoOp()
    next3.isCompleted should be(true)
  }

  it should "complete CompletionStages from `nextAction` immediately if `sendAction` has already been invoked" in {
    val channel = new SimpleInputChannel()

    channel.sendNoOp()
    channel.nextAction.isCompleted should be(true)

    channel.sendNoOp()
    channel.sendNoOp()
    channel.nextAction.isCompleted should be(true)
    channel.nextAction.isCompleted should be(true)
  }
}

object SimpleInputChannelTest {
  implicit def CompletionStageToFuture[A](cs: CompletionStage[A]): Future[A] = toScala(cs)

  implicit final class ChannelNoOp(private val channel: InputChannel) extends AnyVal {
    def sendNoOp()(implicit iac: InputActionCreator): Unit = channel.sendAction(iac.inputAction(_ => Unit))
  }
}
