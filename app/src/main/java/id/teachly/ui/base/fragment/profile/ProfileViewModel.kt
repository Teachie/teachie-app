package id.teachly.ui.base.fragment.profile

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.teachly.utils.Helpers.loadDrawable
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    fun loadImage(img: String, context: Context, onSuccess: (Drawable) -> Unit) =
        viewModelScope.launch {
            onSuccess(img.loadDrawable(context))
        }
}