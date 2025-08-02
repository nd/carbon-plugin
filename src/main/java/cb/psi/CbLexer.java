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

  static {
    validBin['_'] = true;
    validDec['_'] = true;
    validHex['_'] = true;

    for (int i = '0'; i <= '1'; i++) {
      validBin[i] = true;
    }

    for (int i = '0'; i <= '9'; i++) {
      validDec[i] = true;
      validHex[i] = true;
    }

    for (int i = 'A'; i <= 'F'; i++) {
      validHex[i] = true;
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
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        readNumber();
        break;

      default: {
        myTokenStart = myOffset;
        myToken = TokenType.BAD_CHARACTER;
        myOffset++;
        myTokenEnd = myOffset;
      }
      break;
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
      if (valid[c & 0xff]) {
        myOffset++;
      } else {
        break;
      }
    }

    if (allowReal && myOffset + 1 < myEndOffset) {
      c = myBuffer.charAt(myOffset);
      if (c == '.' && valid[myBuffer.charAt(myOffset + 1) & 0xff]) {
        myOffset += 2;
        while (myOffset < myEndOffset) {
          c = myBuffer.charAt(myOffset);
          if (valid[c & 0xff]) {
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
            if (valid[c & 0xff]) {
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
}
