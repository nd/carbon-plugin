package cb.psi

import cb.CbLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType

class CbAstNodeType(name: String, val id: Int) : IElementType(name, CbLanguage) {
  companion object {
    val FILE: IFileElementType = IFileElementType(CbLanguage)

    @JvmField val LIBRARY_DECLARATION = CbAstNodeType("LIBRARY_DECLARATION", CbAstNodeTypeId.LIBRARY_DECLARATION_ID)
    @JvmField val IMPL_LIBRARY_DECLARATION = CbAstNodeType("IMPL_LIBRARY_DECLARATION", CbAstNodeTypeId.IMPL_LIBRARY_DECLARATION_ID)
    @JvmField val PACKAGE_DECLARATION = CbAstNodeType("PACKAGE_DECLARATION", CbAstNodeTypeId.PACKAGE_DECLARATION_ID)
    @JvmField val IMPL_PACKAGE_DECLARATION = CbAstNodeType("IMPL_PACKAGE_DECLARATION", CbAstNodeTypeId.IMPL_PACKAGE_DECLARATION_ID)
    @JvmField val IMPORT = CbAstNodeType("IMPORT", CbAstNodeTypeId.IMPORT_ID)
  }
}

class CbAstNodeTypeId {
  companion object {
    const val LIBRARY_DECLARATION_ID = 1
    const val IMPL_LIBRARY_DECLARATION_ID = 2
    const val PACKAGE_DECLARATION_ID = 3
    const val IMPL_PACKAGE_DECLARATION_ID = 4
    const val IMPORT_ID = 5
  }
}
