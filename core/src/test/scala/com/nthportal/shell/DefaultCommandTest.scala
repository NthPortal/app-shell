package com.nthportal.shell

class DefaultCommandTest extends SimpleSpec {
  behavior of s"Default ${nameOf[Command]}"

  it should "return `None` for `description` and `help` methods" in {
    val c = new Command {
      override val name: String = "something"

      override def execute(args: ImmutableSeq[String])(implicit sink: OutputSink): Unit = {}
    }

    c.description should be(None)
    c.help(Nil) should be(None)
    c.help(List(c.name)) should be(None)
    c.help(List("a", "list", "of", "sorts")) should be(None)
  }
}
