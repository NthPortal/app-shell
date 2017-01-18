package com.nthportal.shell.async.compat
import java.util.function.Function

import com.nthportal.shell.compat.Shell

class BasicInputActionCreator(shell: Shell) extends InputActionCreator {
  implicit def mapping = Map(shell.underlying -> shell)

  override def inputAction[T](action: Function[Shell, T]) = new InputAction[T](action(_))
}

object BasicInputActionCreator {
  def apply(shell: Shell): BasicInputActionCreator = new BasicInputActionCreator(shell)
}
