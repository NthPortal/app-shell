package com.nthportal.shell
package internal

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand}

class HelpCommandTest extends SimpleSpec {
  behavior of "HelpCommand"

  it should "have the correct name" in {
    HelpCommand(Nil).name should be(ShellCore.helpCommandName)
  }

  it should "have a description" in {
    HelpCommand(Nil).description should not be empty
  }

  it should "always return the same result for `help`" in {
    val t = TestCommand()
    val h = HelpCommand(List(t))
    val res = h.help(Nil)
    h.help(List(h.name)) shouldEqual res
    h.help(List(t.name)) shouldEqual res
    h.help(List("not-a-command with args")) shouldEqual res
  }

  it should "never write an empty message to the OutputSink" in {
    val t1 = TestCommand()
    val t2 = new TestCommand("t2") {
      override def description: Option[String] = Some("A test command")

      override def help(args: ImmutableSeq[String]): Option[String] = Some(" \t \t")
    }
    val h = HelpCommand(List(t1, t2))

    val os1 = StatefulOutputProvider()
    h.execute(Nil)(os1)
    os1.writtenTo should be(true)

    val os2 = StatefulOutputProvider()
    h.execute(List(h.name))(os2)
    os2.writtenTo should be(true)

    val os3 = StatefulOutputProvider()
    h.execute(List(t1.name))(os3)
    os3.writtenTo should be(true)

    val os4 = StatefulOutputProvider()
    h.execute(List(t2.name))(os4)
    os4.writtenTo should be(true)

    val os5 = StatefulOutputProvider()
    h.execute(List("not-a-command with args"))(os5)
    os5.writtenTo should be(true)
  }

  it should "tab complete with commands" in {
    val c1 = TestCommand("c1")
    val c2 = TestCommand("c2")
    val help = HelpCommand(List(c1, c2))

    val res1 = help.tabComplete(List(""))
    res1 should contain(c1.name)
    res1 should contain(c2.name)
    res1 should contain(help.name)
    res1 should have length 3

    val res2 = help.tabComplete(List("c"))
    res2 should contain(c1.name)
    res2 should contain(c2.name)
    res2 should have length 2

    help.tabComplete(List("c3")) shouldBe empty
  }
}
