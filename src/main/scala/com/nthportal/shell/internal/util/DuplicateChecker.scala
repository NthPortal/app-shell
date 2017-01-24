package com.nthportal.shell.internal.util

/**
  * Checks strings for duplicates.
  */
private[shell] object DuplicateChecker {
  /**
    * Checks strings for duplicates. If any exist, throws an
    * [[IllegalArgumentException]] containing the specified `descriptor`
    * in its message.
    *
    * @param strings    the strings to check for duplicates
    * @param descriptor the descriptor of the type of the string, to be used
    *                   in an error message
    */
  @throws[IllegalArgumentException]
  def check(strings: Iterable[String], descriptor: String): Unit = {
    val duplicates = strings
      .groupBy(identity)
      .mapValues(_.size)
      .filter(_._2 > 1)
      .keys
    require(duplicates.isEmpty, s"Duplicate $descriptor(s): ${duplicates.mkString(", ")}")
  }
}
