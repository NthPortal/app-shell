package com.nthportal.shell.async

import scala.concurrent.Future

trait InputProvider {
  def nextAction: Future[InputAction[_]]
}
