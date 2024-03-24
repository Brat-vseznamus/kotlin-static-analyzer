import astFeatures.CodeRule
import astFeatures.FunctionIsId
import astFeatures.findByDescription
import astFeatures.findSubTree
import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.AstInfo
import kotlinx.ast.common.ast.DefaultAstTerminal
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import java.nio.file.Paths

data class FunctionInfo(val name: String, val path: String, val position: AstInfo)

fun main() {
    val file = Paths.get(".").toFile()

    println("Run analyzer for ${file.absolutePath}!")

    val candidates = mutableListOf<FunctionInfo>()
    val codeRule: CodeRule = FunctionIsId()

    file.walk()
        .filter { it.isFile && it.name.endsWith(".kt") }
        .filter { !it.absolutePath.contains("resources") }
        .forEach { curFile ->
            val source = AstSource.File(curFile.absolutePath)
            val kotlinFile = KotlinGrammarAntlrKotlinParser.parseKotlinFile(source)

            val fs = kotlinFile.findByDescription("functionDeclaration")
            fs.forEach {
                val namePath = "functionDeclaration > simpleIdentifier > Identifier"
                val names = it.findSubTree(namePath)
                if (names.isNotEmpty()) {
                    val nameNode = names[0] as DefaultAstTerminal
                    val position = (nameNode.attachments.attachments.values).toList()[0]

                    val isId = codeRule.check(it)
                    if (isId && position is AstInfo) {
                        candidates.add(FunctionInfo(nameNode.text, curFile.absolutePath, position))
                    }
                }
            }
        }

    if (candidates.isEmpty()) {
        println("Everything is okay!")
    } else {
        throw Error(
            candidates
                .map { "${it.path}:${it.position.start}: function \"${it.name}\" is similar to identical function" }
                .joinToString(separator = "\n") { it },
        )
    }
}
