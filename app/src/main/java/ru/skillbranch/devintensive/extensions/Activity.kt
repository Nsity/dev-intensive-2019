package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.skillbranch.devintensive.utils.Utils.convertDpToPx

fun Activity.hideKeyboard() {
    val view = currentFocus
    if( view != null) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean{
    val rootView = findViewById<View>(android.R.id.content)
    val visibleBounds = Rect()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = rootView.height - visibleBounds.height()
    val marginOfError = convertDpToPx(this, 50)

    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return this.isKeyboardOpen().not()
}