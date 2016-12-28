package com.nthportal.example.shell.console

import org.scalatest.{FlatSpec, Ignore, Matchers}

/**
  * If you wish to test [[ConsoleShell]], comment out the @[[Ignore]] annotation.
  */
@Ignore
class ConsoleShellTest extends FlatSpec with Matchers {
  "ConsoleShell" should "work properly" in {
    val s = new ConsoleShell
    s.readInput()
  }
}
