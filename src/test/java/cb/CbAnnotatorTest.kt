package cb

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import java.nio.file.Path

class CbAnnotatorTest : BasePlatformTestCase() {

  override fun getTestDataPath(): String {
    return Path.of("src/test/data/analysis/annotator").toAbsolutePath().toString()
  }

  fun doTest() {
    val testName = getTestName(true)
    myFixture.configureByFile("$testName.carbon")
    myFixture.checkHighlighting()
  }

  fun testImplPackage() {
    doTest()
  }

  fun testImplLibrary() {
    doTest()
  }

  fun testDoublePackage() {
    doTest()
  }

  fun testDoubleLibrary() {
    doTest()
  }
}
