package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = currentFocus
    if( view != null) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val permissibleError = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics).toInt()
    val rootView = findViewById<View>(android.R.id.content)
    var rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val difference = rootView.height - rect.height()
    return (difference > permissibleError)
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}