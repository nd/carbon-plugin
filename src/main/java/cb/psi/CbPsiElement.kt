package cb.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

class CbPsiElement(node: ASTNode) : ASTWrapperPsiElement(node), PsiElement {
}