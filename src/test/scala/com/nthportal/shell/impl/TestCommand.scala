package com.nthportal.shell
package impl

class TestCommand extends Command {
  private var _executed: Boolean = false

  def executed: Boolean = _executed

  override val name: String = TestCommand.name

  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = _executed = true
}

object TestCommand {
  val name = "test"
}
