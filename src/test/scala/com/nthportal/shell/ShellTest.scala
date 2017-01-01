package com.nthportal.shell

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand, WriteCommand}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser
import org.scalatest.{FlatSpec, Matchers}

class ShellTest extends FlatSpec with Matchers {
  private val testCommand = new TestCommand
  private val outputProvider = new StatefulOutputProvider
  private val shell = Shell(WhitespaceDelineatingParser, List(testCommand, WriteCommand), outputProvider)

  behavior of "Shell"

  it should "include commands with which it was constructed" in {
    shell.commands should (contain(testCommand) and contain(WriteCommand))
  }

  it should "execute a command" in {
    testCommand.executed should be(false)
    shell.executeLine(testCommand.name)
    testCommand.executed should be(true)
  }

  it should "write to the output provider" in {
    outputProvider.writtenTo should be(false)
    shell.executeLine(WriteCommand.name)
    outputProvider.writtenTo should be(true)
    outputProvider.lines should have length 1
  }

  it should "tab complete properly" in {
    shell.tabComplete("") should contain(testCommand.name)
    shell.tabComplete(testCommand.name.substring(0, 2)) should contain(testCommand.name)
    shell.tabComplete(testCommand.name) should contain(testCommand.name)
    shell.tabComplete(testCommand.name + " other text") should not contain testCommand.name
  }
}
