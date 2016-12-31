package com.nthportal.example.shell.console

import org.scalatest.{FlatSpec, Matchers}

class ConsoleShellTest extends FlatSpec with Matchers {
  "ConsoleShell" should "be constructed (without issue)" in {
    new ConsoleShell // Should not throw an exception
  }

  /** To test the shell, replace `ignore` with `it` */
  ignore should "work properly" in {
    val s = new ConsoleShell
    s.readInput()
  }
}
