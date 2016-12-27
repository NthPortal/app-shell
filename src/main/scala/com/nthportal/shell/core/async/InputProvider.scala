package com.nthportal.shell.core.async

import scala.concurrent.Future

trait InputProvider {
  def nextAction: Future[InputAction[_]]
}
