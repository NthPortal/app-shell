package com.nthportal

package object shell {
  // Type aliases
  type ImmutableSeq[+A] = scala.collection.immutable.Seq[A]
  type ImmutableIterable[+A] = scala.collection.immutable.Iterable[A]
}
