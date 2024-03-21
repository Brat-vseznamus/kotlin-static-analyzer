package astFeatures

import kotlinx.ast.common.ast.Ast
import kotlinx.ast.common.ast.DefaultAstNode
import kotlinx.ast.common.ast.DefaultAstTerminal

interface CodeRule {
    fun check(node: Ast): Boolean
}

class FunctionIsId: CodeRule {
    override fun check(node: Ast): Boolean {
        if (node.description != "functionDeclaration") {
            return false
        }
        val paramNames = mutableSetOf<String>()
        node.findByDescription("parameter").forEach { param ->
            (param as DefaultAstNode).children[0].findByDescription("simpleIdentifier").forEach {
                val identifiers = it.findByDescription("Identifier")
                identifiers.forEach { id ->
                    if (id is DefaultAstTerminal) {
                        paramNames.add(id.text)
                    }
                }
            }
        }
        if (paramNames.size != 1) {
            return false
        }
        val paramName = paramNames.last()

        node.findByDescription("statement").forEach { statement ->
            val exprs = statement.findByDescription("expression")
            exprs.forEach { expr ->
                expr.findByDescription("jumpExpression").forEach {
                    if (it.findByDescription("RETURN").isNotEmpty() && it is DefaultAstNode) {
                        val lastNode = it.children.last()
                        if (lastNode.description == "expression") {
                            val identifiers = lastNode.findByDescription("Identifier")
                            if (identifiers.size == 1) {
                                val maybeVar = identifiers[0]
                                if (maybeVar is DefaultAstTerminal) {
                                    if (maybeVar.text != paramName) {
                                        return false
                                    }
                                }
                            } else {
                                return false
                            }
                        }
                    }
                }
            }
        }
        return true
    }

}