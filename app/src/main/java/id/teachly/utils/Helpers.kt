package id.teachly.utils

import android.os.Handler
import android.os.Looper

object Helpers {

    fun startDelay(second: Int, onComplete: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            onComplete()
        }, (second * 1000).toLong())
    }
}