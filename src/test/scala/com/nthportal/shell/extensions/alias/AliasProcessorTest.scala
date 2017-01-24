package com.nthportal.shell.extensions.alias

import com.nthportal.shell.{Shell, SimpleSpec}
import com.nthportal.shell.impl.{NoOpOutputProvider, TestCommand}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

class AliasProcessorTest extends SimpleSpec {

  behavior of "AliasProcessor"

  it should "process aliases properly" in {
    val command = TestCommand()
    val name = "alias"
    val alias = Alias.forCommand(name, command)
    val processor = AliasProcessor(alias)
    val shell = Shell(WhitespaceDelineatingParser, NoOpOutputProvider, List(processor), command)

    shell.executeLine(name)
    command.executed should be (true)
  }

  it should "not loop infinitely" in {
    val command = TestCommand()
    val name = "alias"
    val a1 = Alias(command.name, name)
    val a2 = Alias.forCommand(name, command)
    val processor = AliasProcessor(a1, a2)
    val shell = Shell(WhitespaceDelineatingParser, NoOpOutputProvider, List(processor), List(command))

    shell.executeLine(command.name)
    command.executed should be (true)
  }
}
