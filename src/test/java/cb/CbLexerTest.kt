package cb

import cb.psi.CbLexer
import cb.psi.CbToken
import com.intellij.lexer.Lexer
import com.intellij.testFramework.LexerTestCase
import java.lang.reflect.Modifier
import java.nio.file.Path


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

  fun testStrings() {
    doTest()
  }

  fun testTokensHaveUniqueIds() {
    val tokenById = hashMapOf<Int, CbToken>()
    val fields = CbToken::class.java.declaredFields
    for (field in fields) {
      if (Modifier.isStatic(field.modifiers) && field.type == CbToken::class.java) {
        val token = field.get(CbToken::class.java) as CbToken
        val clashing = tokenById[token.id]
        if (clashing != null) {
          fail("Token id clash: $token, $clashing")
        } else {
          tokenById[token.id] = token
        }
      }
    }
  }

  fun _testKeywordHash() {
    val initialHash = -3750763034362895579L
    var mul = 104693L
    var maxUnique = 0
    while (true) {
      for (i in 0..8) {
        val uniqueCount = collides(initialHash, mul, i)
        if (uniqueCount > maxUnique) {
          println("$uniqueCount unique: initialHash=$initialHash, mul=$mul, shift=$i")
          maxUnique = uniqueCount
        }
        if (uniqueCount == CbToken.KEYWORDS.size) {
          println("initialHash=$initialHash, mul=$mul, shift=$i")
          return
        }
      }
      mul+=2
    }
  }

  private fun hashChar(hash: Long, c: Char, mul: Long): Long {
    var result = hash xor c.code.toLong()
    result *= mul
    return result
  }

  private fun hashString(initialHash: Long, mul: Long, s: String): Long {
    var result: Long = initialHash
    for (i in 0..<s.length) {
      result = hashChar(result, s.get(i), mul)
    }
    return result
  }

  private fun collides(initialHash: Long, mul: Long, shift: Int): Int {
    val table = Array<CbToken?>(256) { _ -> null}
    var unique = 0
    for (keyword in CbToken.KEYWORDS) {
      val hash = hashString(initialHash, mul, keyword.name)
      val index = ((hash shr shift) and 0xffL).toInt()
      if (table[index] != null) {
        return unique
      }
      else {
        table[index] = keyword
        unique++
      }
    }
    return unique
  }
}