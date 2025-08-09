package cb

import cb.psi.CbLexer
import cb.psi.CbToken
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType


class CbSyntaxHighlighter : SyntaxHighlighterBase() {

  override fun getHighlightingLexer(): Lexer {
    return CbLexer()
  }

  override fun getTokenHighlights(tokenType: IElementType): Array<out TextAttributesKey> {
    return CbSyntaxAttributes.ATTRIBUTES[tokenType] ?: TextAttributesKey.EMPTY_ARRAY
  }
}


class CbSyntaxAttributes {
  companion object {

    val ATTRIBUTES = HashMap<IElementType, Array<TextAttributesKey>>().apply {
      put(CbToken.OPEN_PAREN, arrayOf(DefaultLanguageHighlighterColors.PARENTHESES))
      put(CbToken.CLOSE_PAREN, arrayOf(DefaultLanguageHighlighterColors.PARENTHESES))
      put(CbToken.OPEN_BRACE, arrayOf(DefaultLanguageHighlighterColors.BRACES))
      put(CbToken.CLOSE_BRACE, arrayOf(DefaultLanguageHighlighterColors.BRACES))
      put(CbToken.OPEN_BRACKET, arrayOf(DefaultLanguageHighlighterColors.BRACKETS))
      put(CbToken.CLOSE_BRACKET, arrayOf(DefaultLanguageHighlighterColors.BRACKETS))
      put(CbToken.COMMA, arrayOf(DefaultLanguageHighlighterColors.COMMA))
      put(CbToken.SEMI, arrayOf(DefaultLanguageHighlighterColors.SEMICOLON))
      put(CbToken.PERIOD, arrayOf(DefaultLanguageHighlighterColors.DOT))
      put(CbToken.COMMENT, arrayOf(DefaultLanguageHighlighterColors.LINE_COMMENT))
      put(CbToken.IDENTIFIER, arrayOf(DefaultLanguageHighlighterColors.IDENTIFIER))
      put(CbToken.NUMBER, arrayOf(DefaultLanguageHighlighterColors.NUMBER))
      put(CbToken.STRING, arrayOf(DefaultLanguageHighlighterColors.STRING))
      put(CbToken.RAW_STRING, arrayOf(DefaultLanguageHighlighterColors.STRING))
      put(CbToken.BLOCK_STRING, arrayOf(DefaultLanguageHighlighterColors.STRING))
      put(CbToken.RAW_BLOCK_STRING, arrayOf(DefaultLanguageHighlighterColors.STRING))

      CbToken.OPERATORS.forEach {
        put(it, arrayOf(DefaultLanguageHighlighterColors.OPERATION_SIGN))
      }

      CbToken.KEYWORDS.forEach {
        put(it, arrayOf(DefaultLanguageHighlighterColors.KEYWORD))
      }

      put(TokenType.BAD_CHARACTER, arrayOf(HighlighterColors.BAD_CHARACTER))
    }
  }
}
