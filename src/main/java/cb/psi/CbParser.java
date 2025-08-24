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
          case CbTokenId.LIBRARY, CbTokenId.PACKAGE -> parsePackage(b);
          case CbTokenId.IMPL -> parseImpl(b);
          case CbTokenId.IMPORT -> parseImport(b);
          default -> b.advanceLexer();
        }
      }
    }
    m.done(root);
    return b.getTreeBuilt();
  }


  private void parsePackage(@NotNull PsiBuilder b) {
    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    if (b.getTokenType() == CbToken.IMPL) {
      b.advanceLexer();
    }

    boolean valid = true;
    if (b.getTokenType() == CbToken.PACKAGE) {
      b.advanceLexer();
      if (!consume(b, CbToken.IDENTIFIER, CbToken.CORE, "Package name expected")) {
        recoverUntilSemiOrNewline(b, line);
        valid = false;
      }
    }

    if (valid) {
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
    m.done(CbAstNodeType.PACKAGE_DECLARATION);
  }


  private void parseImpl(@NotNull PsiBuilder b) {
    IElementType next = b.lookAhead(1);
    int id = next instanceof CbToken cbToken ? cbToken.getId() : 0;
    switch (id) {
      case CbTokenId.LIBRARY, CbTokenId.PACKAGE -> parsePackage(b);
      default -> b.advanceLexer();
    }
  }


  private void parseImport(@NotNull PsiBuilder b) {
    assert(b.getTokenType() == CbToken.IMPORT);

    int line = myLine;
    PsiBuilder.Marker m = b.mark();
    b.advanceLexer();
    boolean hasPackage = false;
    boolean recover = false;
    IElementType afterImport = b.getTokenType();
    if (afterImport == CbToken.IDENTIFIER) {
      b.advanceLexer();
      hasPackage = true;
    } else if (afterImport == CbToken.CORE) {
      b.advanceLexer();
      hasPackage = true;
    }

    if (b.getTokenType() == CbToken.LIBRARY) {
      b.advanceLexer();
      if (b.getTokenType() == CbToken.STRING) {
        b.advanceLexer();
      } else if (b.getTokenType() == CbToken.DEFAULT) {
        b.advanceLexer();
      } else {
        b.error("Library name expected");
        recover = true;
      }
    } else if (!hasPackage) {
      b.error("Package or library expected");
      recover = true;
    }

    if (recover) {
      recoverUntilSemiOrNewline(b, line);
    } else {
      consume(b, CbToken.SEMI, "Missing semicolon");
    }

    m.done(CbAstNodeType.IMPORT);
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
