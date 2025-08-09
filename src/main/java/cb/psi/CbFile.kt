package cb.psi

import cb.CbFileType
import cb.CbLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class CbFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, CbLanguage) {
  override fun getFileType(): FileType {
    return CbFileType.INSTANCE
  }
}