package id.teachly.ui.createsection

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import id.teachly.databinding.ActivityCreateSectionBinding
import id.teachly.utils.Helpers.tag
import kotlinx.android.synthetic.main.activity_create_section.*

class CreateSectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateSectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.richEditor.apply {
            setEditorWidth(360)
            setEditorFontSize(20)
            setEditorFontColor(Color.BLACK)
            setPlaceholder("Tulis di sini")

            setOnTextChangeListener { Log.d(this@CreateSectionActivity.tag(), "onChange: $it") }
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

            //img, video, link
        }

    }
}