package cb.psi.elements

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

open class CbPsiElement(node: ASTNode) : ASTWrapperPsiElement(node), PsiElement {
}
