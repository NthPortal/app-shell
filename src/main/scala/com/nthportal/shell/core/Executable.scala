package com.nthportal.shell.core

trait Executable {
  def execute(args: Seq[String]): Option[String]
}
