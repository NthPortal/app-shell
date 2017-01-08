package com.nthportal.shell.compat.async

import com.nthportal.shell.async.{InputAction => SInputAction, InputProvider => SInputProvider}
import com.nthportal.shell.compat.async.{InputAction => JInputAction, InputProvider => JInputProvider}
import com.nthportal.shell.compat.{Shell => JShell}
import com.nthportal.shell.{Shell => SShell}

import scala.compat.java8.FutureConverters
import scala.concurrent.{ExecutionContext, Future}

private[async] class SCompatInputProvider(inputProvider: JInputProvider)
                                         (implicit mapping: SShell => JShell,
                                          ec: ExecutionContext) extends SInputProvider {
  override def nextAction: Future[SInputAction[_]] = {
    FutureConverters.toScala(inputProvider.nextAction())
      .map(asScalaInputAction(_))
  }

  private def asScalaInputAction[T](inputAction: JInputAction[T]): SInputAction[T] = {
    (shell: SShell) => inputAction.action(mapping(shell))
  }
}
