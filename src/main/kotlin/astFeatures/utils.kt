package astFeatures

import kotlinx.ast.common.ast.Ast
import kotlinx.ast.common.ast.DefaultAstNode

fun Ast.findByDescription(description: String): List<Ast> {
    val results = mutableListOf<Ast>()

    if (this.description == description) {
        results.add(this)
    }

    val x = this
    if (x is DefaultAstNode) {
        x.children.forEach {
            results.addAll(it.findByDescription(description))
        }
    }

    return results
}

fun Ast.findSubTree(description: String): List<Ast> {
    return this.findSubTreeByTags(description.split(">").map { it.trim() })
}

private fun Ast.findSubTreeByTags(tags: List<String>): List<Ast> {
    if (tags.isEmpty()) {
        return listOf()
    }
    val prefix = tags[0]
    val suffix = tags.subList(1, tags.size)
    if (this.description == prefix) {
        if (suffix.isEmpty()) {
            return listOf(this)
        } else {
            if (this is DefaultAstNode) {
                return this.children.map { it.findSubTreeByTags(suffix) }.flatten()
            }
        }
    }
    return listOf()
}
