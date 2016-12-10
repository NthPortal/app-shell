package com.nthportal.shell.core

trait TabCompletable {
  def tabComplete(args: Seq[String]): Seq[String]
}
