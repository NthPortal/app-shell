package com.nthportal.shell.core

trait SubCommand extends TabCompletable {
  def execute(args: Seq[String]): Option[String]

  def description: Option[String] = None

  def help(args: Seq[String]): Option[String] = None

  def tabComplete(args: Seq[String]): Seq[String] = Nil
}
