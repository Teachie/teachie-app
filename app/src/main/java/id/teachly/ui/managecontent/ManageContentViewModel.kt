package id.teachly.ui.managecontent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.teachly.data.Space
import id.teachly.data.Story
import id.teachly.repo.remote.firebase.auth.Auth
import id.teachly.repo.remote.firebase.firestore.FirestoreSpace
import id.teachly.repo.remote.firebase.firestore.FirestoreStory
import kotlinx.coroutines.launch

class ManageContentViewModel : ViewModel() {

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>> get() = _stories

    private val _space = MutableLiveData<List<Space>>()
    val space: LiveData<List<Space>> get() = _space

    fun loadStories() = viewModelScope.launch {
        FirestoreStory.getStoryByUserId(Auth.getCurrentUser()?.uid ?: "") {
            _stories.value = it
        }
    }

    fun loadSpace() = viewModelScope.launch {
        FirestoreSpace.getSpaceByUserId(Auth.getCurrentUser()?.uid ?: "") { _, list ->
            _space.value = list
        }
    }

}