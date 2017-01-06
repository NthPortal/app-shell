package com.nthportal.shell
package internal

import com.nthportal.shell.impl.{StatefulOutputProvider, TestCommand}
import org.scalatest.{FlatSpec, Matchers}

class ShellCoreTest extends FlatSpec with Matchers {
  behavior of "ShellCore"

  it should "validate commands properly" in {
    an[IllegalArgumentException] should be thrownBy {ShellCore(TestCommand(ShellCore.helpCommandName))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(TestCommand("contains whitespace"))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(TestCommand("contains\twhitespace"))}

    an[IllegalArgumentException] should be thrownBy {ShellCore(TestCommand(), TestCommand())}

    a[NullPointerException] should be thrownBy {ShellCore(null: Command)}
  }

  it should "not write to the OutputSink when executing an empty argument list" in {
    val os = StatefulOutputProvider()
    val core = ShellCore(TestCommand())
    core.execute(Nil)(os)
    os.writtenTo should be(false)
  }

  it should "write to the OutputSink when executing a nonexistent command" in {
    val core = ShellCore(TestCommand())

    val os1 = StatefulOutputProvider()
    core.execute(List(""))(os1)
    os1.writtenTo should be(true)

    val os2 = StatefulOutputProvider()
    core.execute(List("not-a-command"))(os2)
    os2.writtenTo should be(true)
  }
}
