package com.nthportal.shell
package impl

class TestCommand(override val name: String) extends Command {
  def this() = this(TestCommand.name)

  private var _executed: Boolean = false

  def executed: Boolean = _executed

  override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = _executed = true
}

object TestCommand {
  val name = "test"

  def apply(): TestCommand = new TestCommand

  def apply(name: String): TestCommand = new TestCommand(name)
}
