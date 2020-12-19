package id.teachly.ui.publishsection

import android.net.Uri
import androidx.lifecycle.ViewModel
import id.teachly.data.Story
import id.teachly.repo.remote.firebase.firestore.FirestoreStory
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.utils.Helpers

class PublishSectionViewModel : ViewModel() {
    fun publishWithThumbnail(story: Story, img: Uri) {
        StorageUser.storeImgStory(img) { b, url ->
            if (b) {
                story.thumbnail = url
                publishStory(story)
            }
        }
    }

    fun publishStory(story: Story) {
        FirestoreStory.publishNewStory(story) {
            Helpers.hideLoadingDialog()
        }
    }
}