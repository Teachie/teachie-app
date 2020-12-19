package id.teachly.ui.detaildiscussion

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.teachly.data.Discussion
import id.teachly.data.Response
import id.teachly.repo.remote.firebase.firestore.FirestoreDiscussion
import id.teachly.repo.remote.firebase.storage.StorageUser
import kotlinx.coroutines.launch

class DetailDiscussionViewModel : ViewModel() {

    private val _discussion = MutableLiveData<List<Discussion>>()
    private val _response = MutableLiveData<List<Response>>()
    val discussion: LiveData<List<Discussion>> get() = _discussion
    val response: LiveData<List<Response>> get() = _response

    fun getDiscussion(storyId: String) = viewModelScope.launch {
        FirestoreDiscussion.getDiscussionByStoryId(storyId) { b, list ->
            _discussion.value = list
        }
    }

    fun postNewResponse(response: Response, discussionId: String, total: Int) {
        FirestoreDiscussion.createNewResponse(response)
        FirestoreDiscussion.updateResponseTotal(discussionId, total)
    }

    fun getResponse(discussionId: String) = viewModelScope.launch {
        FirestoreDiscussion.getResponseByDiscussionId(discussionId) { b, list ->
            _response.value = list
        }
    }

    fun createNewDiscussionWithImage(discussion: Discussion, img: Uri, context: Context) {
        StorageUser.storeImgDiscussion(img) { b, url ->
            discussion.img = url
            createNewDiscussion(discussion, context)
        }
    }

    fun createNewDiscussion(discussion: Discussion, context: Context) {
        FirestoreDiscussion.createNewDiscussion(discussion) {
            if (it) (context as Activity).finish()
        }
    }

}