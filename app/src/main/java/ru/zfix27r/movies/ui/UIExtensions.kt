package ru.zfix27r.movies.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snack(msg: String) {
    return Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}