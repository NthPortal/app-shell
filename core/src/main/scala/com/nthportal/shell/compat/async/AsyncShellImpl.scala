package com.nthportal.shell.compat.async

import com.nthportal.shell.async.{AsyncShell => SAsyncShell}
import com.nthportal.shell.compat.async.{AsyncShell => JAsyncShell}
import com.nthportal.shell.compat.{Shell => JShell}

import scala.concurrent.Future

private[async] class AsyncShellImpl(inputProvider: InputProvider)(implicit shell: JShell) extends JAsyncShell {
  implicit private val mapping = Map(shell.underlying -> shell)
  implicit private val ec = SAsyncShell.defaultContext

  private val asyncShell = SAsyncShell(new SCompatInputProvider(inputProvider))(shell.underlying, ec)

  override def terminate0(): Future[Void] = asyncShell.terminate().map(_ => null)
}

private[async] object AsyncShellImpl {
  def apply(inputProvider: InputProvider, shell: JShell): AsyncShellImpl = new AsyncShellImpl(inputProvider)(shell)
}
