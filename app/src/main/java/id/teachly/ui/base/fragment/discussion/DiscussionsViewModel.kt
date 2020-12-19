package id.teachly.ui.base.fragment.discussion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.teachly.data.Discussion
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreDiscussion
import kotlinx.coroutines.launch

class DiscussionsViewModel : ViewModel() {

    private val _discussion = MutableLiveData<List<Discussion>>()

    val discussion: LiveData<List<Discussion>> = _discussion

    fun getMyDiscussion() = viewModelScope.launch {
        FirestoreDiscussion.getDiscussionByUserId(Auth.getCurrentUser()?.uid ?: "") { _, list ->
            _discussion.value = list
        }
    }
}