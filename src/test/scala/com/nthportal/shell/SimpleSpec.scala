package com.nthportal.shell

import org.scalatest.{FlatSpec, Matchers}

abstract class SimpleSpec extends FlatSpec with Matchers {
  def nameOf(obj: Any): String = obj.getClass.getSimpleName.stripSuffix("$")
}
