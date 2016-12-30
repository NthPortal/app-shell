package com.nthportal.shell.async

import scala.concurrent.Future

trait InputChannel extends InputProvider {
  def sendAction[T](action: InputAction[T]): Future[T]
}
