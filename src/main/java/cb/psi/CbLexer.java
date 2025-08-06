package cb.psi;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CbLexer extends LexerBase {

  private static final boolean[] empty = new boolean[256];
  private static final boolean[] validBin = new boolean[256];
  private static final boolean[] validDec = new boolean[256];
  private static final boolean[] validHex = new boolean[256];
  private static final boolean[] validWordStart = new boolean[256];
  private static final boolean[] validWordMiddle = new boolean[256];

  private static final long initialHash = -3750763034362895579L;
  private static final long[] keywordHashes = new long[256];
  private static final CbToken[] keywords = new CbToken[256];

  static {
    validBin['_'] = true;
    validDec['_'] = true;
    validHex['_'] = true;
    validWordStart['_'] = true;
    validWordMiddle['_'] = true;

    for (int i = '0'; i <= '1'; i++) {
      validBin[i] = true;
    }

    for (int i = '0'; i <= '9'; i++) {
      validDec[i] = true;
      validHex[i] = true;
      validWordMiddle[i] = true;
    }

    for (int i = 'A'; i <= 'F'; i++) {
      validHex[i] = true;
    }

    for (int i = 'a'; i <= 'z'; i++) {
      validWordStart[i] = true;
      validWordMiddle[i] = true;
    }
    for (int i = 'A'; i <= 'Z'; i++) {
      validWordStart[i] = true;
      validWordMiddle[i] = true;
    }

    for (CbToken keyword : CbToken.keywords) {
      hashKeyword(keyword);
    }
  }


  private CharSequence myBuffer;
  private int myOffset;
  private int myEndOffset;
  private int myState;

  private IElementType myToken;
  private int myTokenStart;
  private int myTokenEnd;


  @Override
  public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
    myBuffer = buffer;
    myOffset = startOffset;
    myEndOffset = endOffset;
    myState = initialState;
    advance();
  }

  @Override
  public void advance() {
    if (myOffset >= myEndOffset) {
      myToken = null;
      return;
    }
    char c = myBuffer.charAt(myOffset);
    switch (c) {
      case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> readNumber();
      case ' ', '\t' -> readWhitespace();
      case '\n' -> readToken(CbToken.NEWLINE);
      case '/' -> readCommentOrSlash();
      case '.' -> readToken(CbToken.PERIOD);
      case '(' -> readToken(CbToken.OPEN_PAREN);
      case ')' -> readToken(CbToken.CLOSE_PAREN);
      case '[' -> readToken(CbToken.OPEN_BRACKET);
      case ']' -> readToken(CbToken.CLOSE_BRACKET);
      case '{' -> readToken(CbToken.OPEN_BRACE);
      case '}' -> readToken(CbToken.CLOSE_BRACE);
      case ';' -> readToken(CbToken.SEMI);
      case ',' -> readToken(CbToken.COMMA);
      case '?' -> readToken(CbToken.QUESTION);
      case '%' -> readTokenEq(CbToken.PERCENT, CbToken.PERCENT_EQ);
      case '&' -> readTokenEq(CbToken.AMP, CbToken.AMP_EQ);
      case '^' -> readTokenEq(CbToken.CARET, CbToken.CARET_EQ);
      case '*' -> readTokenEq(CbToken.STAR, CbToken.STAR_EQ);
      case '~' -> readTokenEq(CbToken.TILDE, CbToken.TILDE_EQ);
      case '|' -> readTokenEq(CbToken.PIPE, CbToken.PIPE_EQ);
      case '!' -> readTokenEq(CbToken.EXCL, CbToken.EXCL_EQ);
      case '+' -> readTokenEq(CbToken.PLUS, CbToken.PLUS_EQ, '+', CbToken.PLUS_PLUS);
      case ':' -> readTokenEq(CbToken.COLON, CbToken.COLON_EQ, '!', CbToken.COLON_EXCL);
      case '=' -> readTokenEq(CbToken.EQ, CbToken.EQ_EQ, '>', CbToken.EQ_GREATER);
      case '-' -> readMinus();
      case '>' -> readGreater();
      case '<' -> readLess();
      default -> {
        if (!readWord()) {
          readToken(TokenType.BAD_CHARACTER);
        }
      }
    }
  }

  private void readNumber() {
    myTokenStart = myOffset;
    myToken = CbToken.NUMBER;
    char c = myBuffer.charAt(myOffset++);
    boolean[] valid = empty;
    char exponent = 0;
    boolean allowReal = true;
    if (c == '0' && myOffset < myEndOffset) {
      char base = myBuffer.charAt(myOffset);
      if (base == 'b') {
        valid = validBin;
        allowReal = false;
        myOffset++;
      }
      if (base == 'x') {
        valid = validHex;
        exponent = 'p';
        myOffset++;
      }
    } else {
      valid = validDec;
      exponent = 'e';
    }

    while (myOffset < myEndOffset) {
      c = myBuffer.charAt(myOffset);
      if (isValid(valid, c)) {
        myOffset++;
      } else {
        break;
      }
    }

    if (allowReal && myOffset + 1 < myEndOffset) {
      c = myBuffer.charAt(myOffset);
      if (c == '.' && isValid(valid, myBuffer.charAt(myOffset + 1))) {
        myOffset += 2;
        while (myOffset < myEndOffset) {
          c = myBuffer.charAt(myOffset);
          if (isValid(valid, c)) {
            myOffset++;
          } else {
            break;
          }
        }

        if (myOffset + 1 < myEndOffset && myBuffer.charAt(myOffset) == exponent) {
          myOffset++;
          c = myBuffer.charAt(myOffset);
          if (c == '+' || c == '-') {
            myOffset++;
          }
          valid = validDec;
          while (myOffset < myEndOffset) {
            c = myBuffer.charAt(myOffset);
            if (isValid(valid, c)) {
              myOffset++;
            } else {
              break;
            }
          }
        }
      }
    }
    myTokenEnd = myOffset;
  }

  private static boolean isValid(boolean[] validArray, char c) {
    return c < validArray.length && validArray[c];
  }

  private void readWhitespace() {
    myToken = CbToken.WHITESPACE;
    myTokenStart = myOffset;
    myOffset++;
    while (myOffset < myEndOffset) {
      char c = myBuffer.charAt(myOffset);
      if (c == ' ' || c == '\t') {
        myOffset++;
      } else {
        break;
      }
    }
    myTokenEnd = myOffset;
  }

  private void readCommentOrSlash() {
    myTokenStart = myOffset;
    if (myOffset + 1 < myEndOffset && myBuffer.charAt(myOffset + 1) == '/') {
      myToken = CbToken.COMMENT;
      myOffset += 2;
      while (myOffset < myEndOffset && myBuffer.charAt(myOffset) != '\n') {
        myOffset++;
      }
      myTokenEnd = myOffset;
    } else {
      readTokenEq(CbToken.SLASH, CbToken.SLASH_EQ);
    }
  }

  private void readToken(@NotNull IElementType token) {
    myToken = token;
    myTokenStart = myOffset;
    myOffset++;
    myTokenEnd = myOffset;
  }

  private void readTokenEq(@NotNull CbToken token0, @NotNull CbToken token1) {
    myTokenStart = myOffset;
    myToken = token0;
    int length = 1;
    if (myOffset + 1 < myEndOffset && myBuffer.charAt(myOffset + 1) == '=') {
      myToken = token1;
      length = 2;
    }
    myOffset += length;
    myTokenEnd = myOffset;
  }

  private void readTokenEq(
          @NotNull CbToken token1,
          @NotNull CbToken tokenEq,
          char next, @NotNull CbToken token2) {
    myTokenStart = myOffset;
    myToken = token1;
    int length = 1;
    if (myOffset + 1 < myEndOffset) {
      char c = myBuffer.charAt(myOffset + 1);
      if (c == '=') {
        myToken = tokenEq;
        length = 2;
      } else if (c == next) {
        myToken = token2;
        length = 2;
      }
    }
    myOffset += length;
    myTokenEnd = myOffset;
  }

  private void readMinus() {
    myTokenStart = myOffset;
    myToken = CbToken.MINUS;
    int length = 1;
    if (myOffset + 1 < myEndOffset) {
      char c = myBuffer.charAt(myOffset + 1);
      if (c == '-') {
        myToken = CbToken.MINUS_MINUS;
        length = 2;
      } else if (c == '>') {
        myToken = CbToken.MINUS_GREATER;
        length = 2;
      } else if (c == '=') {
        myToken = CbToken.MINUS_EQ;
        length = 2;
      }
    }
    myOffset += length;
    myTokenEnd = myOffset;
  }

  private void readGreater() {
    // >
    // >=
    // >>
    // >>=
    myTokenStart = myOffset;
    myToken = CbToken.GREATER;
    int length = 1;
    if (myOffset + 1 < myEndOffset) {
      char c = myBuffer.charAt(myOffset + 1);
      if (c == '=') {
        myToken = CbToken.GREATER_EQ;
        length = 2;
      } else if (c == '>') {
        myToken = CbToken.GREATER_GREATER;
        length = 2;
        if (myOffset + 2 < myEndOffset && myBuffer.charAt(myOffset + 2) == '=') {
          myToken = CbToken.GREATER_GREATER_EQ;
          length = 3;
        }
      }
    }
    myOffset += length;
    myTokenEnd = myOffset;
  }

  private void readLess() {
    // <
    // <=
    // <>
    // <-
    // <<
    // <<=
    myTokenStart = myOffset;
    myToken = CbToken.LESS;
    int length = 1;
    if (myOffset + 1 < myEndOffset) {
      char c = myBuffer.charAt(myOffset + 1);
      if (c == '=') {
        myToken = CbToken.LESS_EQ;
        length = 2;
      } else if (c == '>') {
        myToken = CbToken.LESS_GREATER;
        length = 2;
      } else if (c == '-') {
        myToken = CbToken.LESS_MINUS;
        length = 2;
      } else if (c == '<') {
        myToken = CbToken.LESS_LESS;
        length = 2;
        if (myOffset + 2 < myEndOffset && myBuffer.charAt(myOffset + 2) == '=') {
          myToken = CbToken.LESS_LESS_EQ;
          length = 3;
        }
      }
    }
    myOffset += length;
    myTokenEnd = myOffset;
  }

  private boolean readWord() {
    char c = myBuffer.charAt(myOffset);
    if (!isValid(validWordStart, c)) {
      return false;
    }
    myTokenStart = myOffset;
    myOffset++;
    long hash = hashChar(initialHash, c);
    boolean potentialNumericTypeLiteral = c == 'i' || c == 'u' || c == 'f';
    boolean raw = c == 'r' && myOffset < myEndOffset && myBuffer.charAt(myOffset) == '#';
    if (raw) {
      myOffset++;
    }
    while (myOffset < myEndOffset) {
      c = myBuffer.charAt(myOffset);
      if (!isValid(validWordMiddle, c)) {
        break;
      }
      hash = hashChar(hash, c);
      potentialNumericTypeLiteral &= isValid(validDec, c);
      myOffset++;
    }
    myToken = potentialNumericTypeLiteral ? getNumericTypeLiteral(myTokenStart, myOffset) : null;
    if (myToken == null) {
      myToken = getKeyword(hash, myTokenStart, myOffset);
    }
    if (myToken == null) {
      myToken = raw ? CbToken.RAW_IDENTIFIER : CbToken.IDENTIFIER;
    }
    myTokenEnd = myOffset;
    return true;
  }

  // should be called if the token matches [iuf][0-9]*
  private @Nullable CbToken getNumericTypeLiteral(int tokenStart, int tokenEnd) {
    if (tokenStart + 1 == tokenEnd) {
      // no digits after [iuf]
      return null;
    }
    if (myBuffer.charAt(tokenStart + 1) == '0') {
      return null;
    }
    int n = 0;
    for (int i = tokenStart + 1; i < tokenEnd; i++) {
      n = n * 10 + (myBuffer.charAt(i) - '0');
      if (n < 0) {
        return null;
      }
    }
    return (n & 0b111) == 0 ? CbToken.NUMERIC_TYPE_LITERAL : null;
  }

  @Override
  public @Nullable IElementType getTokenType() {
    return myToken;
  }

  @Override
  public int getTokenStart() {
    return myTokenStart;
  }

  @Override
  public int getTokenEnd() {
    return myTokenEnd;
  }

  @Override
  public @NotNull CharSequence getBufferSequence() {
    return myBuffer;
  }

  @Override
  public int getBufferEnd() {
    return myEndOffset;
  }

  @Override
  public int getState() {
    return myState;
  }


  private static int getKeywordIndex(long hash) {
    // This happens to produce a unique index for all keywords.
    // If keywords are changed this needs to be updated (hashKeyword will throw).
    return (int) ((hash >> 4) & 0xff);
  }

  private static long hashChar(long hash, char c) {
    long result = hash ^ c;
    result *= 104693;
    return result;
  }

  private static long hashString(String s) {
    long result = initialHash;
    for (int i = 0; i < s.length(); i++) {
      result = hashChar(result, s.charAt(i));
    }
    return result;
  }

  private static void hashKeyword(CbToken keyword) {
    long hash = hashString(keyword.getName());
    int index = getKeywordIndex(hash);
    if (keywordHashes[index] != 0 || keywords[index] != null) {
      throw new IllegalStateException("Hash doesn't produce unique index for all keywords");
    }
    keywordHashes[index] = hash;
    keywords[index] = keyword;
  }

  private @Nullable CbToken getKeyword(long hash, int startOffset, int endOffset) {
    int index = getKeywordIndex(hash);
    if (keywordHashes[index] != hash) {
      return null;
    }
    CbToken keyword = keywords[index];
    if (keyword == null) {
      return null;
    }
    int length = endOffset - startOffset;
    String keywordString = keyword.getName();
    if (keywordString.length() != length) {
      return null;
    }
    for (int i = 0; i < length; i++) {
      if (keywordString.charAt(i) != myBuffer.charAt(startOffset + i)) {
        return null;
      }
    }
    return keyword;
  }
}
