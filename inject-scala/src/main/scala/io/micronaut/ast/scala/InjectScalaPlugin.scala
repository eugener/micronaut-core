package io.micronaut.ast.scala

import scala.tools.nsc.{Global, Phase}
import scala.tools.nsc.plugins._
import scala.tools.nsc.transform._

class InjectScalaPlugin(override val global: Global) extends Plugin {
    override val name = "micronaut-inject-scala-plugin"
    override val description = "Micronaut Inject Scala plugin"
    override val components = List(new InjectScalaPluginComponent(global))
}

class InjectScalaPluginComponent(val global: Global) extends PluginComponent with TypingTransformers {

    import global._

    override val phaseName: String = "micronaut-inject-scala-phase"
    override val runsAfter: List[String] = List("parser")

    override def newPhase(prev: Phase): Phase = new StdPhase(prev) {
        override def apply(unit: CompilationUnit) {
            unit.body = new MicronautInjector(unit).transform(unit.body)
        }
    }

    class MicronautInjector(unit: CompilationUnit) extends TypingTransformer(unit) {

        override def transform(tree: Tree): global.Tree = ???

    }

    def newTransformer(unit: CompilationUnit) = new MicronautInjector(unit)

}

