package cb.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiUtilCore

class CbParserDefinition : ParserDefinition {

  override fun createLexer(project: Project): Lexer {
    return CbLexer()
  }

  override fun createParser(project: Project): PsiParser {
    return CbParser()
  }

  override fun getFileNodeType(): IFileElementType {
    return CbNodeType.FILE
  }

  override fun getCommentTokens(): TokenSet {
    return CbToken.COMMENTS
  }

  override fun getStringLiteralElements(): TokenSet {
    return CbToken.STRINGS
  }

  override fun getWhitespaceTokens(): TokenSet {
    return CbToken.WHITESPACES
  }

  override fun createElement(node: ASTNode): PsiElement {
    return PsiUtilCore.NULL_PSI_ELEMENT
  }

  override fun createFile(viewProvider: FileViewProvider): PsiFile {
    return CbFile(viewProvider)
  }
}