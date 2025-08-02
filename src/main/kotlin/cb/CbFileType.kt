package cb

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class CbFileType : LanguageFileType(CbLanguage) {

  companion object {
    @JvmField
    val INSTANCE: CbFileType = CbFileType()
  }

  override fun getName(): @NonNls String {
    return "Carbon"
  }

  override fun getDescription(): @NlsContexts.Label String {
    return "Carbon"
  }

  override fun getDefaultExtension(): @NlsSafe String {
    return "carbon"
  }

  override fun getIcon(): Icon {
    return AllIcons.FileTypes.Text
  }
}