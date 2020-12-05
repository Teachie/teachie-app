package id.teachly.utils

import android.os.Handler
import android.os.Looper

object Helpers {

    fun startDelay(second: Int, onComplete: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            onComplete()
        }, (second * 1000).toLong())
    }

    val dummyAva =
        "https://avatars3.githubusercontent.com/u/32610660?s=460&u=f2945b508ae75d9d543473286dcf788318e731e9&v=4"
    val dummyTopic = "https://1000logos.net/wp-content/uploads/2016/10/Android-Logo.png"
    val dummyBg =
        "https://lh3.googleusercontent.com/-66JyZlj-KVag0V6qViVUHtvfYS0u33sPt2tQweip5xnBD0yrMoVPyer3xZerJG9FhtxuWV8wPy1tOWOXmsq-b90KeC2zkxNPVar"
}