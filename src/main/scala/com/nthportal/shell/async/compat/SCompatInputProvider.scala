package com.nthportal.shell.async.compat

import com.nthportal.shell.async.{InputAction => SInputAction, InputProvider => SInputProvider}
import com.nthportal.shell.compat.{Shell => JShell}
import com.nthportal.shell.{Shell => SShell}

import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

private[compat] case class SCompatInputProvider(inputProvider: InputProvider)
                                               (implicit mapping: SShell => JShell,
                                                ec: ExecutionContext) extends SInputProvider {
  override def nextAction: Future[SInputAction[_]] = toScala(inputProvider.nextAction()).map(_.underlying)
}
