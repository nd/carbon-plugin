package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IFileElementType

class CbNodeType {
  companion object {
    val FILE: IFileElementType = IFileElementType(CbLanguage)
  }
}