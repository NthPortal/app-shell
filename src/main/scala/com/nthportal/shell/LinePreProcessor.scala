package com.nthportal.shell

/**
  * Pre-processes a line before it is used by a [[Shell]].
  */
trait LinePreProcessor {
  /**
    * Pre-processes a line before it is used by a [[Shell]] by taking a
    * tokenized input line, and returning a (possibly) modified tokenized line.
    * (In this context, 'tokenized input line' just means a line which has
    * already been broken up into arguments.)
    *
    * For example, this could be used to implement aliasing for commands,
    * whereby the first tokenized string of the line may be replaced by one
    * or more strings.
    *
    * `lineParser` can be used to tokenize replacement or additional text,
    * if needed.
    *
    * @param parsedLine the input line which is already parsed into tokens
    * @param lineParser a function to parse a string into tokens (if needed)
    * @return a possibly modified line, as parsed tokens
    */
  def apply(parsedLine: ImmutableSeq[String], lineParser: String => ImmutableSeq[String]): ImmutableSeq[String]
}
