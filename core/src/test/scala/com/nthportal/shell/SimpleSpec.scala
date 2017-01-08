package com.nthportal.shell

import org.scalatest.{FlatSpec, Matchers}

import scala.language.experimental.macros

abstract class SimpleSpec extends FlatSpec with Matchers {
  def nameOf[C]: String = macro NameOf.macroImpl[C]

  def nameOf(obj: Any): String = obj.getClass.getSimpleName.stripSuffix("$")
}
