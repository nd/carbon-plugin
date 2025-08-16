package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

class CbAstNodeType(name: String) : IElementType(name, CbLanguage) {
  companion object {
    val FILE: IFileElementType = IFileElementType(CbLanguage)

    @JvmField val LIBRARY_DECLARATION = CbAstNodeType("LIBRARY_DECLARATION")
    @JvmField val IMPL_LIBRARY_DECLARATION = CbAstNodeType("IMPL_LIBRARY_DECLARATION")
    @JvmField val PACKAGE_DECLARATION = CbAstNodeType("PACKAGE_DECLARATION")
  }
}