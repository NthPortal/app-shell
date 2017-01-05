package com.nthportal.example.shell.console

import org.scalatest.{FlatSpec, Matchers}

class ConsoleShellTest extends FlatSpec with Matchers {
  behavior of "ConsoleShell"

  it should "be constructed (without issue)" in {
    noException should be thrownBy {new ConsoleShell}
  }

  /** To test the shell, replace `ignore` with `it` */
  ignore should "work properly" in {
    val s = new ConsoleShell
    s.readInput()
  }
}
