package cb

import java.nio.file.Path
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
    val relativePath = dirPath + "/" + getTestName(true) + extension
    return Path.of(relativePath).toAbsolutePath().toString()
  }

  fun doTest() {
    doFileTest("carbon")
  }

  fun testNumbers() {
    doTest()
  }

  fun testSpaces() {
    doTest()
  }

  fun testComments() {
    doTest()
  }

  fun testSymbolic() {
    doTest()
  }

  fun testWords() {
    doTest()
  }
}