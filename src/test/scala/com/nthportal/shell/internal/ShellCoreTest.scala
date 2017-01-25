package com.nthportal.shell
package internal

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand}

class ShellCoreTest extends SimpleSpec {
  behavior of classOf[ShellCore].getSimpleName

  it should "validate commands properly" in {
    an[IllegalArgumentException] should be thrownBy {ShellCore(List(TestCommand(ShellCore.helpCommandName)))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(List(TestCommand("contains whitespace")))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(List(TestCommand("contains\twhitespace")))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(List(TestCommand(), TestCommand()))}

    a[NullPointerException] should be thrownBy {ShellCore(List(null: Command))}
  }

  it should "not write to the OutputSink when executing an empty argument list" in {
    val os = StatefulOutputProvider()
    val core = ShellCore(List(TestCommand()))
    core.execute(Nil)(os)
    os.writtenTo should be(false)
  }

  it should "write to the OutputSink when executing a nonexistent command" in {
    val core = ShellCore(List(TestCommand()))

    val os1 = StatefulOutputProvider()
    core.execute(List(""))(os1)
    os1.writtenTo should be(true)

    val os2 = StatefulOutputProvider()
    core.execute(List("not-a-command"))(os2)
    os2.writtenTo should be(true)
  }

  it should "check equality properly" in {
    val t = TestCommand()
    val core = ShellCore(List(t))
    core shouldEqual ShellCore(List(t))
    core should not equal "something which isn't a ShellCore"
  }
}
