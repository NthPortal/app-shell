package com.nthportal.shell
package async

class SimpleInputChannelTest extends SimpleSpec {

  import SimpleInputChannelTest._

  behavior of nameOf[SimpleInputChannel]

  it should "complete Futures from `nextAction` after `sendAction` is invoked" in {
    val channel = SimpleInputChannel()

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

  it should "complete Futures from `nextAction` immediately if `sendAction` has already been invoked" in {
    val channel = SimpleInputChannel()

    channel.sendNoOp()
    channel.nextAction.isCompleted should be(true)

    channel.sendNoOp()
    channel.sendNoOp()
    channel.nextAction.isCompleted should be(true)
    channel.nextAction.isCompleted should be(true)
  }
}

object SimpleInputChannelTest {

  implicit final class ChannelNoOp(private val channel: InputChannel) extends AnyVal {
    def sendNoOp(): Unit = channel.sendAction(_ => Unit)
  }

}
