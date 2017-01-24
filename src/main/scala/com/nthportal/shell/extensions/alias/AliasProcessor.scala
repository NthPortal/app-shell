package com.nthportal.shell
package extensions.alias

import scala.annotation.tailrec

/**
  * A [[LinePreProcessor]] for [[Alias aliases]].
  *
  * If the first argument of an input line is [[Alias.name the name of an alias]],
  * it replaces that argument with [[Alias.expansion the alias's expansion]].
  *
  * @param aliases the aliases with which to process the line
  */
final class AliasProcessor private(aliases: Map[String, Alias]) extends LinePreProcessor {
  override def apply(parsedLine: ImmutableSeq[String],
                     lineParser: String => ImmutableSeq[String]): ImmutableSeq[String] = {
    parsedLine match {
      case Seq() => parsedLine
      case name +: tail =>
        @tailrec
        def expand(possible: String, used: Set[Alias] = Set.empty, suffix: List[String] = Nil): ImmutableSeq[String] = {
          aliases.get(possible) match {
            case Some(alias) =>
              if (used contains alias) possible :: suffix
              else {
                val parsed = lineParser(alias.expansion)
                expand(parsed.head, used + alias, parsed.tail ++: suffix)
              }
            case None => possible :: suffix
          }
        }

        expand(name) ++: tail
    }
  }
}

object AliasProcessor {
  /**
    * Creates an [[AliasProcessor]] from a sequence of [[Alias aliases]].
    *
    * @param aliases the aliases with which to process the line
    * @return an AliasProcessor with the specified aliases
    */
  def apply(aliases: ImmutableSeq[Alias]): AliasProcessor = {
    val aliasMap = aliases.map(a => (a.name, a)).toMap
    validateAliases(aliasMap)
    new AliasProcessor(aliasMap)
  }

  /**
    * Creates an [[AliasProcessor]] from a sequence of [[Alias aliases]].
    *
    * @param aliases the aliases with which to process the line
    * @return an AliasProcessor with the specified aliases
    */
  def apply(aliases: Alias*): AliasProcessor = apply(aliases.to[ImmutableSeq])

  private def validateAliases(aliases: Map[String, Alias]): Unit = {
    val duplicates = aliases.keys
      .groupBy(identity)
      .mapValues(_.size)
      .filter(_._2 > 1)
      .keys
    require(duplicates.isEmpty, s"Duplicated alias name(s): ${duplicates.mkString(", ")}")
  }
}
