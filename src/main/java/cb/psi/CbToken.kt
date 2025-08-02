package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType

class CbToken(debugName: String) : IElementType(debugName, CbLanguage) {
  companion object {
    @JvmField val NUMBER = CbToken("number")
  }
}