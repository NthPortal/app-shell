package com.nthportal.shell

/**
  * Something which can be tab-completed (by a [[Shell]]).
  */
trait TabCompletable {
  /**
    * Returns a sequence of suggested completions for the final argument
    * of a sequence of arguments. Returns an empty sequence if no suggestions
    * are available for a given argument sequence.
    *
    * @param args the arguments for which to provide suggested completions
    * @return a sequence of suggested completions for the final argument given
    */
  def tabComplete(args: ImmutableSeq[String]): ImmutableSeq[String]
}
