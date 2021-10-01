package com.bohregard.shared.extension

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Create a toast message with a context class
 *
 * @param T the type param with a super of Context
 * @param text the string resource to show in the text
 */
inline fun <reified T: Context> T.makeShortText(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Create a toast message with a context class
 *
 * @param T the type param with a super of Context
 * @param text the string to show in the text
 */
inline fun <reified T: Context> T.makeShortText(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Create a toast message with a context class
 *
 * @param T the type param with a super of Context
 * @param text the string resource to show in the text
 */
inline fun <reified T: Context> T.makeLongText(@StringRes text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Create a toast message with a context class
 *
 * @param T the type param with a super of Context
 * @param text the string to show in the text
 */
inline fun <reified T: Context> T.makeLongText(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Loop through and find the first Activity
 *
 * @return
 */
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
