package com.lji.nfcwriter

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

@DslMarker
annotation class MJsonMarker

@MJsonMarker
class Json() : JSONObject() {

    constructor(json: Json.() -> Unit) : this() {
        this.json()
    }
    infix fun <T> String.to(value: T) {
        put(this, value)
    }
}

fun View.errToast(msg : String){
    val snackbar = Snackbar.make(this,msg,Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(this.context,R.color.error))
    snackbar.show()
}

fun View.successToast(msg : String){
    val snackbar = Snackbar.make(this,msg,Snackbar.LENGTH_LONG)
    snackbar.setBackgroundTint(ContextCompat.getColor(this.context,R.color.green))
    snackbar.show()
}