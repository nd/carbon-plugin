package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

class CbAstNodeType(name: String, val id: Int) : IElementType(name, CbLanguage) {
  companion object {
    val FILE: IFileElementType = IFileElementType(CbLanguage)

    @JvmField val PACKAGE_DECLARATION = CbAstNodeType("PACKAGE_DECLARATION", CbAstNodeTypeId.PACKAGE_DECLARATION_ID)
    @JvmField val IMPORT = CbAstNodeType("IMPORT", CbAstNodeTypeId.IMPORT_ID)
  }
}

class CbAstNodeTypeId {
  companion object {
    const val PACKAGE_DECLARATION_ID = 1
    const val IMPORT_ID = 2
  }
}
