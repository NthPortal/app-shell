package com.nthportal.shell.compat
package impl

import java.util
import java.util.Optional
import java.util.stream.Collectors

import com.nthportal.shell.OutputSink

class TestCommand(_name: String) extends Command {
  def this() = this(TestCommand.name)

  private var _executed: Boolean = false

  def executed: Boolean = _executed

  override def name: String = _name

  override def execute(args: util.List[String], sink: OutputSink): Unit = _executed = true

  override def description(): Optional[String] = Optional.of("A test command")

  override def help(args: util.List[String]): Optional[String] = {
    Optional.of("help: " + args.stream().collect(Collectors.joining(" ")))
  }

  override def tabComplete(args: util.List[String]): util.List[String] = args
}

object TestCommand {
  val name = "test"

  def apply(): TestCommand = new TestCommand

  def apply(name: String): TestCommand = new TestCommand(name)
}
