package com.nthportal.shell
package compat.async

import java.util

import com.nthportal.shell.async.{InputAction => SInputAction}
import com.nthportal.shell.compat.async.{InputAction => JInputAction}
import com.nthportal.shell.compat.{Shell => JShell}

import scala.collection.JavaConverters
import scala.concurrent.Future

private[async] class JCompatInputAction[T](inputAction: SInputAction[T]) extends JInputAction[T] {
  override def future(): Future[T] = inputAction.future

  override def action(shell: JShell): T = inputAction.action(shell.underlying)
}

private[async] object JCompatInputAction {
  def tabCompletion(line: String): JInputAction[util.List[String]] = {
    val action = SInputAction.tabCompletion(line)
    val mapped: SInputAction[util.List[String]] = shell => JavaConverters.seqAsJavaList(action.action(shell))
    new JCompatInputAction(mapped)
  }

  def execution(line: String): JInputAction[Void] = {
    val action = SInputAction.execution(line)
    val mapped: SInputAction[Void] = shell => {action.action(shell); null}
    new JCompatInputAction(mapped)
  }
}
