package cb.psi.elements;

import cb.psi.CbAstNodeType;
import cb.psi.CbFile;
import com.intellij.lang.ASTNode;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static cb.psi.CbAstNodeTypeId.IMPORT_ID;
import static cb.psi.CbAstNodeTypeId.PACKAGE_DECLARATION_ID;

public class CbElementFactory {

  public static @NotNull CbFile createFile(@NotNull FileViewProvider viewProvider) {
    return new CbFile(viewProvider);
  }

  public static PsiElement createElement(@NotNull ASTNode node) {
    IElementType nodeType = node.getElementType();
    CbAstNodeType cbNodeType = nodeType instanceof CbAstNodeType ? (CbAstNodeType) nodeType : null;
    int nodeId = cbNodeType != null ? cbNodeType.getId() : 0;
    return switch (nodeId) {
      case PACKAGE_DECLARATION_ID -> new CbPackage(node);
      case IMPORT_ID -> new CbImport(node);
      default -> new CbPsiElement(node);
    };
  }
}
