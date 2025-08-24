package cb.psi.elements

import cb.psi.CbToken
import com.intellij.lang.ASTNode
import com.intellij.psi.impl.source.tree.TreeUtil

class CbImport(node: ASTNode) : CbPsiElement(node) {

  fun getExport(): ASTNode? {
    return node.firstChildNode?.takeIf { it.elementType == CbToken.EXPORT }
  }

  fun getPackageIdentifier(): ASTNode? {
    val importToken = node.findChildByType(CbToken.IMPORT)
    val afterImport = TreeUtil.skipWhitespaceAndComments(importToken?.treeNext, true)
    if (afterImport?.elementType == CbToken.IDENTIFIER || afterImport?.elementType == CbToken.CORE) {
      return afterImport
    }
    return null
  }

}