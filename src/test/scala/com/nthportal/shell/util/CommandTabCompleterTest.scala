package com.nthportal.shell
package util

import com.nthportal.shell.impl.TestCommand

class CommandTabCompleterTest extends SimpleSpec {
  behavior of classOf[CommandTabCompleter].getSimpleName

  it should "return an empty seq when given an empty list of arguments" in {
    val ctc = new CommandTabCompleter {
      override protected def commands = List(TestCommand())
    }

    ctc.tabComplete(Nil) shouldBe empty
  }
}
