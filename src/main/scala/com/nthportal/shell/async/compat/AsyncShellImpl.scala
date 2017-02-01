package com.nthportal.shell.async.compat

import java.util.function.Function

import com.nthportal.shell.async.compat.{AsyncShell => JAsyncShell}
import com.nthportal.shell.async.{AsyncShell => SAsyncShell}
import com.nthportal.shell.compat.{Shell => JShell}

import scala.concurrent.Future

private[compat] class AsyncShellImpl(inputProvider: InputProvider, shell: JShell) extends JAsyncShell {
  implicit private val mapping = Map(shell.underlying -> shell)
  implicit private val ec = SAsyncShell.defaultContext
  private val asyncShell = SAsyncShell(SCompatInputProvider(inputProvider))(shell.underlying, ec)

  override val status0: Future[Void] = asyncShell.status map { _ => null }

  override def terminate0(): Future[Void] = {
    asyncShell.terminate()
    status0
  }

  override def inputAction[T](action: Function[JShell, T]): InputAction[T] = new InputAction[T](action(_))
}
