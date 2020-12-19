package id.teachly.ui.createsection

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.github.dhaval2404.imagepicker.ImagePicker
import id.teachly.databinding.ActionbarTopicBinding
import id.teachly.databinding.ActivityCreateSectionBinding
import id.teachly.repo.remote.firebase.storage.StorageUser
import id.teachly.ui.publishsection.PublishSectionActivity
import id.teachly.utils.Helpers
import kotlinx.android.synthetic.main.activity_create_section.*

class CreateSectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateSectionBinding
    private lateinit var actionBarBinding: ActionbarTopicBinding
    private var currentContent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSectionBinding.inflate(layoutInflater)
        actionBarBinding = binding.layoutActionBarCreateSection
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarBinding.apply {
            this.tvTitle.text = "Buat Bagian Baru"
            this.button.apply {
                text = "Publish"
                setOnClickListener {
                    startActivity(
                        Intent(
                            this@CreateSectionActivity,
                            PublishSectionActivity::class.java
                        ).putExtra(PublishSectionActivity.EXTRA_DATA_STORY, currentContent)
                    )
                }
            }
        }

        binding.richEditor.apply {
            setEditorWidth(360)
            setEditorFontColor(Color.BLACK)
            setPlaceholder("Tulis di sini")

            setOnTextChangeListener { currentContent = it }
        }

        binding.apply {
            actionBold.setOnClickListener { richEditor.setBold() }
            actionBlockquote.setOnClickListener { richEditor.setBlockquote() }
            actionHeading1.setOnClickListener { richEditor.setHeading(1) }
            actionHeading2.setOnClickListener { richEditor.setHeading(2) }
            actionHeading3.setOnClickListener { richEditor.setHeading(3) }
            actionInsertBullets.setOnClickListener { richEditor.setBullets() }
            actionInsertNumbers.setOnClickListener { richEditor.setNumbers() }
            actionItalic.setOnClickListener { richEditor.setItalic() }
            actionUnderline.setOnClickListener { richEditor.setUnderline() }
            actionStrikethrough.setOnClickListener { richEditor.setStrikeThrough() }
            actionInsertImage.setOnClickListener {
                ImagePicker.with(this@CreateSectionActivity)
                    .cropSquare().start { resultCode, data ->
                        when (resultCode) {
                            Activity.RESULT_OK -> {
                                StorageUser.storeImgStory(data?.data ?: "".toUri()) { _, url ->
                                    richEditor.insertImage(url, "", 360)
                                }
                            }
                            ImagePicker.RESULT_ERROR -> {
                                Helpers.showToast(
                                    this@CreateSectionActivity,
                                    ImagePicker.getError(data)
                                )
                            }
                            else -> {
                            }
                        }
                    }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}