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
          case CbTokenId.LIBRARY -> parseLibrary(b, false);
          case CbTokenId.PACKAGE -> parsePackage(b, false);
          case CbTokenId.IMPL -> parseImpl(b);
          default -> b.advanceLexer();
        }
      }
    }
    m.done(root);
    return b.getTreeBuilt();
  }


  private void parseImpl(@NotNull PsiBuilder b) {
    IElementType next = b.lookAhead(1);
    int id = next instanceof CbToken cbToken ? cbToken.getId() : 0;
    switch (id) {
      case CbTokenId.LIBRARY -> parseLibrary(b, true);
      case CbTokenId.PACKAGE -> parsePackage(b, true);
      default -> b.advanceLexer();
    }
  }


  private void parseLibrary(@NotNull PsiBuilder b, boolean isImpl) {
    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    if (isImpl) {
      assert(b.getTokenType() == CbToken.IMPL);
      b.advanceLexer();
    }
    assert(b.getTokenType() == CbToken.LIBRARY);
    b.advanceLexer();

    if (!consume(b, CbToken.STRING, "Library name expected")) {
      recoverUntilSemiOrNewline(b, line);
    }
    else {
      consume(b, CbToken.SEMI, "Missing semicolon");
    }
    m.done(isImpl ? CbAstNodeType.IMPL_LIBRARY_DECLARATION : CbAstNodeType.LIBRARY_DECLARATION);
  }


  private void parsePackage(@NotNull PsiBuilder b, boolean isImpl) {
    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    if (isImpl) {
      assert(b.getTokenType() == CbToken.IMPL);
      b.advanceLexer();
    }
    assert(b.getTokenType() == CbToken.PACKAGE);
    b.advanceLexer();

    if (!consume(b, CbToken.IDENTIFIER, CbToken.CORE, "Package name expected")) {
      recoverUntilSemiOrNewline(b, line);
    } else {
      if (consume(b, CbToken.LIBRARY)) {
        if (!consume(b, CbToken.STRING, "Library name expected")) {
          recoverUntilSemiOrNewline(b, line);
        }
        else {
          consume(b, CbToken.SEMI, "Missing semicolon");
        }
      } else {
        consume(b, CbToken.SEMI, "Missing semicolon");
      }
    }
    m.done(isImpl ? CbAstNodeType.IMPL_PACKAGE_DECLARATION : CbAstNodeType.PACKAGE_DECLARATION);
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


  private static boolean consume(@NotNull PsiBuilder b,
                                 @NotNull IElementType token1,
                                 @NotNull IElementType token2,
                                 @Nullable String error) {
    IElementType t = b.getTokenType();
    if (t == token1 || t == token2) {
      b.advanceLexer();
      return true;
    }
    if (error != null) {
      b.error(error);
    }
    return false;
  }


  private void recoverUntilSemiOrNewline(@NotNull PsiBuilder b, int startLine) {
    IElementType t;
    while ((t = b.getTokenType()) != null) {
      if (t == CbToken.SEMI) {
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
