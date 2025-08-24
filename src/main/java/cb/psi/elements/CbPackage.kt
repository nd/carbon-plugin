package cb.psi.elements

import cb.psi.CbToken
import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.tree.TreeUtil

class CbPackage(node: ASTNode) : CbPsiElement(node) {

  fun isImpl(): Boolean {
    return node.firstChildNode?.elementType == CbToken.IMPL
  }

  fun hasPackage(): Boolean {
    val firstChild = node.firstChildNode
    return firstChild?.elementType == CbToken.PACKAGE ||
            TreeUtil.skipWhitespaceAndComments(firstChild?.treeNext, true)?.elementType == CbToken.PACKAGE
  }

}