package cb.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.WhitespaceSkippedCallback;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CbParser implements PsiParser, WhitespaceSkippedCallback {

  private int myLine = 0;

  @Override
  public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder b) {
    b.setWhitespaceSkippedCallback(this);

    PsiBuilder.Marker m = b.mark();
    IElementType token;
    while ((token = b.getTokenType()) != null) {
      if (token instanceof CbToken cbToken) {
        switch (cbToken.getId()) {
          case CbTokenId.LIBRARY:
            parseLibrary(b);
            break;
          case CbTokenId.IMPL:
            parseImpl(b);
            break;
          default:
            b.advanceLexer();
            break;
        }
      }
    }
    m.done(root);
    return b.getTreeBuilt();
  }


  private void parseImpl(@NotNull PsiBuilder b) {
    if (b.lookAhead(1) == CbToken.LIBRARY) {
      parseImplLibrary(b);
    }
    else {
      b.advanceLexer();
    }
  }


  private void parseLibrary(@NotNull PsiBuilder b) {
    assert(b.getTokenType() == CbToken.LIBRARY);
    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    b.advanceLexer(); // library
    if (!consume(b, CbToken.STRING, "Library name expected")) {
      recoverUntilTokenOrNewline(b, CbToken.SEMI, line);
    }
    else {
      consume(b, CbToken.SEMI, "Missing semicolon");
    }
    m.done(CbAstNodeType.LIBRARY_DECLARATION);
  }


  private void parseImplLibrary(@NotNull PsiBuilder b) {
    assert(b.getTokenType() == CbToken.IMPL);
    assert(b.lookAhead(1) == CbToken.LIBRARY);
    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    b.advanceLexer(); // impl
    b.advanceLexer(); // library
    if (!consume(b, CbToken.STRING, "Library name expected")) {
      recoverUntilTokenOrNewline(b, CbToken.SEMI, line);
    }
    else {
      consume(b, CbToken.SEMI, "Missing semicolon");
    }
    m.done(CbAstNodeType.IMPL_LIBRARY_DECLARATION);
  }


  private static boolean consume(@NotNull PsiBuilder b, @NotNull IElementType token) {
    return consume(b, token, null);
  }


  private static boolean consume(@NotNull PsiBuilder b, @NotNull IElementType token, @Nullable String error) {
    if (b.getTokenType() == token) {
      b.advanceLexer();
      return true;
    }
    if (error != null) {
      b.error(error);
    }
    return false;
  }


  private void recoverUntilTokenOrNewline(@NotNull PsiBuilder b, @NotNull IElementType token, int startLine) {
    IElementType t;
    while ((t = b.getTokenType()) != null) {
      if (t == token) {
        b.advanceLexer();
        break;
      }
      if (myLine > startLine) {
        break;
      }
      b.advanceLexer();
    }
  }

  @Override
  public void onSkip(IElementType type, int start, int end) {
    if (type == CbToken.NEWLINE) {
      myLine++;
    }
  }
}
