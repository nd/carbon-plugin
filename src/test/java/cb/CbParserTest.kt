package cb

import cb.psi.CbParserDefinition
import com.intellij.testFramework.ParsingTestCase
import java.nio.file.Path

class CbParserTest : ParsingTestCase("parser", "carbon", CbParserDefinition()) {

  override fun getTestDataPath(): String {
    return Path.of("src/test/data").toAbsolutePath().toString()
  }

  override fun getTestName(lowercaseFirstLetter: Boolean): String {
    return super.getTestName(true)
  }

  fun doTest() {
    doTest(true)
  }

  fun testLibrary() {
    doTest()
  }

  fun testPackage() {
    doTest()
  }

  fun testImport() {
    doTest()
  }
}