package com.nthportal.shell
package extensions.alias

import scala.annotation.tailrec

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
  def apply(aliases: ImmutableSeq[Alias]): AliasProcessor = {
    val aliasMap = aliases.map(a => (a.name, a)).toMap
    validateAliases(aliasMap)
    new AliasProcessor(aliasMap)
  }

  private def validateAliases(aliases: Map[String, Alias]): Unit = {
    val duplicates = aliases.keys
      .groupBy(identity)
      .mapValues(_.size)
      .filter(_._2 > 1)
      .keys
    require(duplicates.isEmpty, s"Duplicated alias name(s): ${duplicates.mkString(", ")}")
  }
}
