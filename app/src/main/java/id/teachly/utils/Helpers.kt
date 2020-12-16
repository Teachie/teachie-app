@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package id.teachly.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import cn.pedant.SweetAlert.SweetAlertDialog
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Timestamp
import id.teachly.R
import java.text.SimpleDateFormat
import java.util.*

object Helpers {

    fun startDelay(second: Int, onComplete: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            onComplete()
        }, (second * 1000).toLong())
    }

    val errorLoginMessage =
        mapOf(
            "There is no user record corresponding to this identifier. The user may have been deleted." to "User tidak ditemukan",
            "The email address is badly formatted." to "Email tidak valid",
            "The email address is already in use by another account." to "Email sudah terdaftar",
            "The password is invalid or the user does not have a password." to "Password salah",
            "The given password is invalid. [ Password should be at least 6 characters ]" to "Password paling sedikit 6 karakter"
        )

    val errorEmptyLoginMessage =
        listOf("Email tidak boleh kosong", "Password tidak boleh kosong", "Password tidak sama")

    val errorEmptyFillDataMessage =
        listOf(
            "Nama tidak boleh kosong",
            "Username tidak boleh kosong",
            "Tanggal lahir tidak boleh kosong",
            "Format username salah",
            "Username sudah digunakan",
            "Bio tidak boleh kosong"
        )

    fun Activity.tag(): String {
        return this::class.java.simpleName
    }

    fun Objects.tag(): String {
        return this::class.java.simpleName
    }

    val dummyAva =
        "https://avatars3.githubusercontent.com/u/32610660?s=460&u=f2945b508ae75d9d543473286dcf788318e731e9&v=4"
    val dummyTopic = "https://1000logos.net/wp-content/uploads/2016/10/Android-Logo.png"
    val dummyBg =
        "https://lh3.googleusercontent.com/-66JyZlj-KVag0V6qViVUHtvfYS0u33sPt2tQweip5xnBD0yrMoVPyer3xZerJG9FhtxuWV8wPy1tOWOXmsq-b90KeC2zkxNPVar"

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getTextWatcher(onTextChanged: () -> Unit) = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            onTextChanged()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    fun getQueryChange(onSearch: (textChange: String?) -> Unit) =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                onSearch(newText)
                return false
            }
        }

    fun SearchView.getQuerySubmit(onSearch: (textChange: String?) -> Unit) {
        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }


    private lateinit var pDialog: SweetAlertDialog
    fun showLoadingDialog(context: Context, message: String = "Loading") {
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor = ContextCompat.getColor(context, R.color.primary)
            titleText = message
            setCancelable(false)
            show()
        }
    }

    fun Toolbar.setToolbarBack(onBack: () -> Unit) {
        this.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { onBack() }
        }
    }

    fun hideLoadingDialog() {
        pDialog.cancel()
    }

    fun View.hideView() {
        this.visibility = View.GONE
    }

    fun View.showView() {
        this.visibility = View.VISIBLE
    }

    fun TextInputLayout.showError(message: String) {
        this.apply {
            error = message
            isErrorEnabled = true
        }
    }

    fun validateError(vararg inputLayouts: TextInputLayout) {
        for (layout in inputLayouts) {
            layout.isErrorEnabled = false
        }
    }

    suspend fun String.loadDrawable(context: Context): Drawable {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(this@loadDrawable)
            .crossfade(true)
            .error(R.drawable.ic_favorite)
            .transformations(CircleCropTransformation())
            .scale(Scale.FILL)
            .build()

        return (loader.execute(request) as SuccessResult).drawable
    }

    fun showDatePicker(activity: Activity, onDateResult: (date: String) -> Unit) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            activity,
            { _, year, month, day ->
                val newDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                onDateResult(
                    Timestamp(newDate.time).formatDate(Const.DateFormat.SIMPLE) ?: ""
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun Timestamp.formatDate(format: String): String? {
        return SimpleDateFormat(format, Locale.getDefault()).format(this.toDate().time)
    }

    fun String.toTimeStamp(format: String): Timestamp {
        return Timestamp(SimpleDateFormat(format, Locale.getDefault()).parse(this))
    }

    fun createBottomSheetDialog(
        context: Context,
        layout: Int,
        onViewCreated: (view: View, dialog: BottomSheetDialog) -> Unit
    ) {
        val view = LayoutInflater.from(context).inflate(layout, null)
        val dialog = BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme)
        dialog.setContentView(view)
        val behaviour = BottomSheetBehavior.from(view.parent as View)
            .apply {
                peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
                expandedOffset = 56
            }
        onViewCreated(view, dialog)
    }

    fun String.capitalizeWords(): String =
        split(" ").joinToString(" ") { it.capitalize(Locale.getDefault()) }

    fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    fun getPageSelected(onChange: (Int) -> Unit) = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) = onChange(position)
        override fun onPageScrollStateChanged(state: Int) {}
    }

}