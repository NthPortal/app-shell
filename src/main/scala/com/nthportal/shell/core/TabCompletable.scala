package com.nthportal.shell
package core

trait TabCompletable {
  def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String]
}
