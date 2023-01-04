package ru.zfix27r.movies.ui.bindings

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import ru.zfix27r.movies.R

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, state: Boolean) {
        view.isVisible = state
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: LinearLayout, stringRes: Int) {
        view.isVisible = stringRes != R.string.empty
    }
}