package id.teachly.ui.publishsection

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModel
import id.teachly.data.Discussion
import id.teachly.data.Space
import id.teachly.data.Story
import id.teachly.repo.remote.firebase.firestore.FirestoreDiscussion
import id.teachly.repo.remote.firebase.firestore.FirestoreSpace
import id.teachly.repo.remote.firebase.firestore.FirestoreStory
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.ui.base.MainActivity
import id.teachly.utils.Helpers
import id.teachly.utils.Helpers.decodeContent

class PublishSectionViewModel : ViewModel() {
    fun publishWithThumbnail(story: Story, img: Uri, context: Context) {
        StorageUser.storeImgStory(img) { b, url ->
            if (b) {
                story.thumbnail = url
                publishStory(story, context)
            }
        }
    }

    fun publishStory(story: Story, context: Context) {
        FirestoreStory.publishNewStory(story) { _, id ->
            createDiscussion(id, story, context)
        }
    }

    fun updateSpace(space: Space) {
        FirestoreSpace.updateSpaceById(space)
    }

    private fun createDiscussion(id: String, story: Story, context: Context) {

        val data = story.content?.decodeContent()
        val dataContent = data?.filter { it.type != 1 }
        val stringBuild = buildString {
            dataContent?.forEach { append(it.data) }
        }

        val discussion = Discussion(
            writerId = story.writerId,
            storyId = id,
            title = story.title,
            question = HtmlCompat.fromHtml(stringBuild, HtmlCompat.FROM_HTML_MODE_COMPACT)
                .toString(),
            img = story.thumbnail,
            category = story.categories,
            time = story.dateCreated
        )

        FirestoreDiscussion.createNewDiscussion(discussion) {
            Helpers.hideLoadingDialog()
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as PublishSectionActivity).finishAffinity()

        }


    }
}