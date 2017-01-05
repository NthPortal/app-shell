package com.nthportal

package object shell {
  type ImmutableSeq[+A] = scala.collection.immutable.Seq[A]
  type ImmutableIterable[+A] = scala.collection.immutable.Iterable[A]
}
