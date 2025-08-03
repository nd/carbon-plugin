package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType

class CbToken(debugName: String) : IElementType(debugName, CbLanguage) {
  companion object {
    @JvmField val WHITESPACE = CbToken("whitespace")
    @JvmField val NEWLINE = CbToken("newline")
    @JvmField val NUMBER = CbToken("number")
  }
}