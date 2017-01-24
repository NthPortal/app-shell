package com.nthportal.shell
package extensions.alias

/**
  * An alias for a [[Shell]] input line.
  *
  * @param name      the name of this alias
  * @param expansion the value with which to replace this alias when
  *                  used in an input line
  */
case class Alias(name: String, expansion: String)
