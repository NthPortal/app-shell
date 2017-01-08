package com.nthportal.shell

import scala.reflect.macros.blackbox

object NameOf {
  def macroImpl[C: c.WeakTypeTag](c: blackbox.Context): c.Expr[String] = {
    import c.universe._

    c.Expr[String](q"classOf[${c.weakTypeOf[C]}].getSimpleName")
  }
}
