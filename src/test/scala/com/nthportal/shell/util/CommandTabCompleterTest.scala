package com.nthportal.shell.util

import com.nthportal.shell.impl.TestCommand
import org.scalatest.{FlatSpec, Matchers}

class CommandTabCompleterTest extends FlatSpec with Matchers {
  behavior of "CommandTabCompleter"

  it should "return an empty seq when given an empty list of arguments" in {
    val ctc = new CommandTabCompleter {
      override protected def commands = List(TestCommand())
    }

    ctc.tabComplete(Nil) should have length 0
  }
}
