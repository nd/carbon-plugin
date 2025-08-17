package cb.psi.elements

import cb.psi.CbAstNodeType
import com.intellij.lang.ASTNode

class CbPackageDeclaration(node: ASTNode) : CbPsiElement(node) {

  fun isImpl(): Boolean {
    return node.elementType == CbAstNodeType.IMPL_PACKAGE_DECLARATION
  }

}
