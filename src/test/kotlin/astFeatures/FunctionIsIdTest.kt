package astFeatures

import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.AstInfo
import kotlinx.ast.common.ast.DefaultAstTerminal
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FunctionIsIdTest {
    @Test
    fun checkIdFunction() {
        assertTrue(init(ID_PATH) == 1)
    }

    @Test
    fun checkNonIdFunction() {
        assertTrue(init(NON_ID_PATH) == 0)
    }

    @Test
    fun checkNonIdBySizeFunction() {
        assertTrue(init(NON_ID_BY_SIZE_PATH) == 0)
    }

    @Test
    fun checkNonIdSinceReturnNonParamFunction() {
        assertTrue(init(NON_ID_RETURNS_NON_PARAM_PATH) == 0)
    }

    fun init(path: String): Int {
        val source = AstSource.File(path)
        val kotlinFile = KotlinGrammarAntlrKotlinParser.parseKotlinFile(source)
        val codeRule: CodeRule = FunctionIsId()

        var numberOfIdFunctions = 0

        val fs = kotlinFile.findByDescription("functionDeclaration")
        fs.forEach {
            val namePath = "functionDeclaration > simpleIdentifier > Identifier"
            val nameNode = it.findSubTree(namePath)[0] as DefaultAstTerminal
            val position = (nameNode.attachments.attachments.values as Collection<AstInfo>).toList()[0]

            val isId = codeRule.check(it)
            if (isId) {
                numberOfIdFunctions++
            }
        }

        return numberOfIdFunctions
    }

    companion object {
        const val ID_PATH = "src/test/resources/Id.kt"
        const val NON_ID_PATH = "src/test/resources/NonId.kt"
        const val NON_ID_BY_SIZE_PATH = "src/test/resources/NonIdBySize.kt"
        const val NON_ID_RETURNS_NON_PARAM_PATH = "src/test/resources/NonIdBecauseOfVar.kt"
    }
}
