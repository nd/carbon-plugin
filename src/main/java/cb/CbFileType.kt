package cb

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.NlsContexts
import org.jetbrains.annotations.NonNls
import javax.swing.Icon

class CbFileType : LanguageFileType(CbLanguage) {

  @Suppress("CompanionObjectInExtension")
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

  override fun getDefaultExtension(): String {
    return "carbon"
  }

  override fun getIcon(): Icon {
    return AllIcons.FileTypes.Text
  }
}