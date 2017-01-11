package com.nthportal.shell
package compat

import java.util

import com.google.common.collect.Lists
import com.nthportal.shell.compat.impl.{TestCommand, TestParser, WriteCommand}
import com.nthportal.shell.impl.StatefulOutputProvider
import Converters._

class ShellTest extends SimpleSpec {
  private val testCommand = TestCommand()
  private val outputProvider = StatefulOutputProvider()
  private val commands = util.Arrays.asList(testCommand, WriteCommand)
  private val shell = Shell.create(TestParser, outputProvider, commands)

  behavior of classOf[Shell].getSimpleName

  it should "produce equivalent shells with both factory methods" in {
    val shell2 = Shell.create(asScalaLineParser(TestParser), outputProvider, commands)
    shell2.commands shouldEqual shell.commands
    shell2.lineParser shouldEqual shell.lineParser
    shell2 shouldEqual shell
  }

  it should "check equality properly" in {
    shell should not equal "not a shell"
  }

  it should "include commands with which it was constructed" in {
    iterableToList(shell.commands) should (contain(testCommand) and contain(WriteCommand))
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
    shell.tabComplete("not-a-command with args") shouldBe empty
  }

  private def iterableToList[T](iterable: java.lang.Iterable[T]): util.List[T] = Lists.newArrayList(iterable)
}
