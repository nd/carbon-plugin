package cb.psi

import cb.psi.elements.CbElementFactory
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

class CbParserDefinition : ParserDefinition {

  override fun createLexer(project: Project): Lexer {
    return CbLexer()
  }

  override fun createParser(project: Project): PsiParser {
    return CbParser()
  }

  override fun getFileNodeType(): IFileElementType {
    return CbAstNodeType.FILE
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
    return CbElementFactory.createElement(node)
  }

  override fun createFile(viewProvider: FileViewProvider): PsiFile {
    return CbElementFactory.createFile(viewProvider)
  }
}