package com.nthportal.shell

trait TabCompletable {
  def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String]
}
