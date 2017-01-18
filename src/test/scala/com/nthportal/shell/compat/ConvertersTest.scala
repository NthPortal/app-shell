package com.nthportal.shell
package compat

import java.util
import java.util.Collections

import com.nthportal.shell.compat.Converters._
import com.nthportal.shell.compat.impl.{TestParser, TestCommand => JTestCommand}
import com.nthportal.shell.impl.{NoOpOutputProvider, TestCommand => STestCommand}
import com.nthportal.shell.parsers.WhitespaceDelineatingParser

import scala.compat.java8.OptionConverters._

class ConvertersTest extends SimpleSpec {
  behavior of nameOf(Converters)

  it should "convert a Java LineParser to a Scala LineParser properly" in {
    val jlp = TestParser
    val slp = asScalaLineParser(jlp)
    asJavaLineParser(slp) should be theSameInstanceAs jlp
    asScalaLineParser(jlp) shouldEqual slp

    // Execution
    val line = "a line to be executed "
    val jExecRes = jlp.parseLineForExecution(line)
    val sExecRes = slp.parseLineForExecution(line)
    testEqualSeq(sExecRes, jExecRes)

    // Tab completion
    val jTabRes = jlp.parseLineForTabCompletion(line)
    val sTabRes = slp.parseLineForTabCompletion(line)
    testEqualSeq(sTabRes, jTabRes)
  }

  it should "convert a Scala LineParser to a Java LineParser properly" in {
    val slp = WhitespaceDelineatingParser
    val jlp = asJavaLineParser(slp)
    asScalaLineParser(jlp) should be theSameInstanceAs slp
    asJavaLineParser(slp) shouldEqual jlp

    // Execution
    val line = "a line to be executed "
    val jExecRes = jlp.parseLineForExecution(line)
    val sExecRes = slp.parseLineForExecution(line)
    testEqualSeq(sExecRes, jExecRes)

    // Tab completion
    val jTabRes = jlp.parseLineForTabCompletion(line)
    val sTabRes = slp.parseLineForTabCompletion(line)
    testEqualSeq(sTabRes, jTabRes)
  }

  it should "convert a Java Command to a Scala Command properly" in {
    val jc = JTestCommand()
    val sc = asScalaCommand(jc)
    asJavaCommand(sc) should be theSameInstanceAs jc
    asScalaCommand(jc) shouldEqual sc

    sc.name should be(jc.name)
    sc.description shouldEqual toScala(jc.description())

    sc.help(Nil) shouldEqual toScala(jc.help(Collections.emptyList()))
    sc.help(List("some", "args")) shouldEqual toScala(jc.help(util.Arrays.asList("some", "args")))

    testEqualSeq(sc.tabComplete(Nil), jc.tabComplete(Collections.emptyList()))
    testEqualSeq(sc.tabComplete(List("")), jc.tabComplete(util.Arrays.asList("")))
    testEqualSeq(
      sc.tabComplete(List("a", "bunch", "of", "args")),
      jc.tabComplete(util.Arrays.asList("a", "bunch", "of", "args"))
    )

    sc.execute(Nil)(NoOpOutputProvider)
    jc.executed should be(true)
  }

  it should "convert a Scala Command to a Java Command properly" in {
    val sc = STestCommand()
    val jc = asJavaCommand(sc)
    asScalaCommand(jc) should be theSameInstanceAs sc
    asJavaCommand(sc) shouldEqual jc

    sc.name should be(jc.name)
    sc.description shouldEqual toScala(jc.description())

    sc.help(Nil) shouldEqual toScala(jc.help(Collections.emptyList()))
    sc.help(List("some", "args")) shouldEqual toScala(jc.help(util.Arrays.asList("some", "args")))

    testEqualSeq(sc.tabComplete(Nil), jc.tabComplete(Collections.emptyList()))
    testEqualSeq(sc.tabComplete(List("")), jc.tabComplete(util.Arrays.asList("")))
    testEqualSeq(
      sc.tabComplete(List("a", "bunch", "of", "args")),
      jc.tabComplete(util.Arrays.asList("a", "bunch", "of", "args"))
    )

    jc.execute(Collections.emptyList(), NoOpOutputProvider)
    sc.executed should be(true)
  }

  it should "convert a Java List to a Scala ImmutableSeq properly" in {
    val list1 = Collections.emptyList()
    testEqualSeq(listToScalaImmutableSeq(list1), list1)

    val list2 = util.Arrays.asList("some", "elements")
    testEqualSeq(listToScalaImmutableSeq(list2), list2)

    val list3 = util.Arrays.asList(new Object)
    testEqualSeq(listToScalaImmutableSeq(list3), list3)
  }

  private def testEqualSeq[T](seq: Seq[T], list: util.List[T]): Unit = {
    seq should have length list.size
    for (e <- seq) list should contain(e)
  }
}
