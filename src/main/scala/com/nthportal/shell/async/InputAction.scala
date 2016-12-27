package com.nthportal.shell
package async

sealed trait InputAction[T] {
  def line: String

  final def apply(): String = line

  private[async] def action(implicit shell: Shell): T
}

case class TabCompletion(line: String) extends InputAction[ImmutableSeq[String]] {
  override private[async] def action(implicit shell: Shell) = shell.tabComplete(line)
}

case class Execution(line: String) extends InputAction[Unit] {
  override private[async] def action(implicit shell: Shell) = shell.executeLine(line)
}

case object Termination
