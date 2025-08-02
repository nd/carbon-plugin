package cb

import cb.psi.CbLexer
import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase

class CbLexerTest : LexerTestCase() {
  override fun createLexer(): Lexer {
    return CbLexer()
  }

  override fun getDirPath(): String {
    return "src/test/data/lexer"
  }

  override fun getPathToTestDataFile(extension: String): String {
    return dirPath + "/" + getTestName(true) + extension
  }

  fun doTest() {
    doFileTest("carbon")
  }

  fun testNumbers() {
    doTest()
  }
}