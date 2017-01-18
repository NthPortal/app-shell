package com.nthportal.shell.compat

import java.util
import java.util.{Collections, Optional}

import com.nthportal.shell.{OutputSink, SimpleSpec}

class DefaultCommandTest extends SimpleSpec {
  behavior of s"Default ${classOf[Command].getSimpleName} (Java)"

  it should "return an empty `Optional` for `description` and `help` methods" in {
    val c = new Command {
      override val name: String = "something"

      override def execute(args: util.List[String], sink: OutputSink): Unit = {}
    }

    c.description shouldEqual Optional.empty()
    c.help(Collections.emptyList()) shouldEqual Optional.empty()
    c.help(util.Arrays.asList(c.name)) shouldEqual Optional.empty()
    c.help(util.Arrays.asList("a", "list", "of", "sorts")) shouldEqual Optional.empty()
    c.tabComplete(util.Arrays.asList("a", "list", "of", "sorts")) shouldBe empty
  }
}
