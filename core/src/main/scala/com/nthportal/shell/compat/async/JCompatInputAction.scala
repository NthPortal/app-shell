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
    new JCompatInputAction(shell => JavaConverters.seqAsJavaList(SInputAction.tabCompletion(line).action(shell)))
  }

  def execution(line: String): JInputAction[Void] = {
    new JCompatInputAction(shell => {SInputAction.execution(line).action(shell); null})
  }
}
