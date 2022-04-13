package com.nordlocker.android_task.ui.shared

import android.graphics.Paint
import android.widget.TextView

fun TextView.strikedThruIf(condition: Boolean) {
    paintFlags = if (condition)
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}
