package com.nthportal.shell

import com.nthportal.shell.impl.StatefulOutputProvider

class DefaultOutputSinkTest extends SimpleSpec {
  behavior of s"Default ${nameOf[OutputSink]}"

  it should "write the same text for both `write` implementations" in {
    val list = List("some", "words")
    val writer1 = StatefulOutputProvider()
    val writer2 = StatefulOutputProvider()

    writer1.write(list)
    writer2.write(list.toString)

    writer1.state shouldEqual writer2.state
  }

  it should "write the same text for both `writeln` implementations" in {
    val list = List("some", "words")
    val writer1 = StatefulOutputProvider()
    val writer2 = StatefulOutputProvider()

    writer1.writeln(list)
    writer2.writeln(list.toString)

    writer1.state shouldEqual writer2.state
  }
}
