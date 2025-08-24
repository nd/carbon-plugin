package cb.analysis

import cb.CbBundle
import cb.psi.elements.CbPackage
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.startOffset

val FIRST_PACKAGE_OR_LIBRARY = Key<CbPackage>("cb.PackageOrLibrary")

class CbAnnotator : Annotator {
  override fun annotate(element: PsiElement, holder: AnnotationHolder) {
    when (element) {
      is CbPackage -> annotatePackage(element, holder)
    }
  }


  private fun annotatePackage(element: CbPackage, holder: AnnotationHolder) {
    saveFirstPackage(element, holder)

    val fileName = element.containingFile.virtualFile?.name ?: return
    val isImplFile = fileName.endsWith(".impl.carbon")
    if (element.isImpl() && !isImplFile) {
      val message = if (element.hasPackage()) {
        CbBundle.message("annotator.package.impl.should.be.in.impl.carbon")
      } else {
        CbBundle.message("annotator.library.impl.should.be.in.impl.carbon")
      }
      val impl = PsiTreeUtil.firstChild(element)
      holder
        .newAnnotation(HighlightSeverity.WARNING, message)
        .range(impl.textRange)
        .create()
    } else if (!element.isImpl() && isImplFile) {
      val message = if (element.hasPackage()) {
        CbBundle.message("annotator.package.api.should.be.in.api.file")
      } else {
        CbBundle.message("annotator.library.api.should.be.in.api.file")
      }
      val pkg = PsiTreeUtil.firstChild(element)
      holder
        .newAnnotation(HighlightSeverity.WARNING, message)
        .range(pkg.textRange)
        .create()
    }
  }


  private fun saveFirstPackage(element: CbPackage, holder: AnnotationHolder) {
    val session = holder.currentAnnotationSession
    val existing = session.getUserData(FIRST_PACKAGE_OR_LIBRARY)
    if (existing != null) {
      reportAlreadyDefinedPackage(element, existing, holder)
    } else {
      session.putUserData(FIRST_PACKAGE_OR_LIBRARY, element)
    }
  }


  private fun reportAlreadyDefinedPackage(
    newDeclaration: CbPackage,
    existingDeclaration: CbPackage,
    holder: AnnotationHolder
  ) {
    val elementToReport = if (newDeclaration.startOffset > existingDeclaration.startOffset) {
      newDeclaration
    } else {
      holder.currentAnnotationSession.putUserData(FIRST_PACKAGE_OR_LIBRARY, newDeclaration)
      existingDeclaration
    }
    val error = if (elementToReport.hasPackage()) {
      CbBundle.message("annotator.package.already.declared")
    } else {
      CbBundle.message("annotator.library.already.declared")
    }
    holder
      .newAnnotation(HighlightSeverity.ERROR, error)
      .range(elementToReport.textRange)
      .create()
  }
}
