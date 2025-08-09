package cb.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType

class CbParser : PsiParser {

  override fun parse(
    root: IElementType,
    builder: PsiBuilder
  ): ASTNode {
    val mark = builder.mark()
    while (builder.tokenType != null) {
      builder.advanceLexer()
    }
    mark.done(root)
    return builder.treeBuilt
  }

}