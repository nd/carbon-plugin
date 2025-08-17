package cb.analysis

import cb.CbBundle
import cb.psi.elements.CbLibraryDeclaration
import cb.psi.elements.CbPackageDeclaration
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.startOffset

val FIRST_PACKAGE_OR_LIBRARY = Key<PsiElement>("cb.PackageOrLibrary")

class CbAnnotator : Annotator {
  override fun annotate(element: PsiElement, holder: AnnotationHolder) {
    when (element) {
      is CbPackageDeclaration -> annotatePackage(element, holder)
      is CbLibraryDeclaration -> annotateLibrary(element, holder)
    }
  }


  private fun annotatePackage(element: CbPackageDeclaration, holder: AnnotationHolder) {
    val session = holder.currentAnnotationSession
    val existing = session.getUserData(FIRST_PACKAGE_OR_LIBRARY)
    if (existing != null) {
      reportAlreadyDefinedLibraryOrPackage(element, existing, holder)
    } else {
      session.putUserData(FIRST_PACKAGE_OR_LIBRARY, element)
    }

    if (element.isImpl()) {
      val fileName = element.containingFile.virtualFile?.name
      if (fileName != null && !fileName.endsWith(".impl.carbon")) {
        val impl = PsiTreeUtil.firstChild(element)
        holder
          .newAnnotation(HighlightSeverity.WARNING, CbBundle.message("annotator.package.impl.should.be.in.impl.carbon"))
          .range(impl.textRange)
          .create()
      }
    }
  }


  private fun annotateLibrary(element: CbLibraryDeclaration, holder: AnnotationHolder) {
    val session = holder.currentAnnotationSession
    val existing = session.getUserData(FIRST_PACKAGE_OR_LIBRARY)
    if (existing != null) {
      reportAlreadyDefinedLibraryOrPackage(element, existing, holder)
    } else {
      session.putUserData(FIRST_PACKAGE_OR_LIBRARY, element)
    }

    if (element.isImpl()) {
      val fileName = element.containingFile.virtualFile?.name
      if (fileName != null && !fileName.endsWith(".impl.carbon")) {
        val impl = PsiTreeUtil.firstChild(element)
        holder
          .newAnnotation(HighlightSeverity.WARNING, CbBundle.message("annotator.library.impl.should.be.in.impl.carbon"))
          .range(impl.textRange)
          .create()
      }
    }
  }


  private fun reportAlreadyDefinedLibraryOrPackage(
    newDeclaration: PsiElement,
    existingDeclaration: PsiElement,
    holder: AnnotationHolder
  ) {
    val elementToReport = if (newDeclaration.startOffset > existingDeclaration.startOffset) {
      newDeclaration
    } else {
      holder.currentAnnotationSession.putUserData(FIRST_PACKAGE_OR_LIBRARY, newDeclaration)
      existingDeclaration
    }
    val error = when (elementToReport) {
      is CbPackageDeclaration -> CbBundle.message("annotator.package.already.declared")
      is CbLibraryDeclaration -> CbBundle.message("annotator.library.already.declared")
      else -> return
    }
    holder
      .newAnnotation(HighlightSeverity.ERROR, error)
      .range(elementToReport.textRange)
      .create()
  }
}
