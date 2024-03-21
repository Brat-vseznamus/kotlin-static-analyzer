import astFeatures.CodeRule
import astFeatures.FunctionIsId
import astFeatures.findByDescription
import astFeatures.findSubTree
import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.AstInfo
import kotlinx.ast.common.ast.DefaultAstTerminal
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser

fun main() {
    // example
    val source = AstSource.File(
        "src/main/kotlin/examples/Id.kt"
    )
    val kotlinFile = KotlinGrammarAntlrKotlinParser.parseKotlinFile(source)
    val codeRule: CodeRule = FunctionIsId()

    var numberOfIdFunctions = 0

    val fs = kotlinFile.findByDescription("functionDeclaration")
    fs.forEach{
        val namePath = "functionDeclaration > simpleIdentifier > Identifier"
        val nameNode = it.findSubTree(namePath)[0] as DefaultAstTerminal
        val position = (nameNode.attachments.attachments.values as Collection<AstInfo>).toList()[0]

        val isId = codeRule.check(it)
        if (isId) {
            numberOfIdFunctions++
            println("function \"${nameNode.text}\" on position ${position.start} is similar to id")
        }
    }

    if (numberOfIdFunctions == 0) {
        println("No functions identical to id were detected!")
    }
}

