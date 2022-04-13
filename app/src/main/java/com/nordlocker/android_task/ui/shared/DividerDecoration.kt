package com.nordlocker.android_task.ui.shared


import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nordlocker.android_task.R
import kotlin.math.roundToInt

class DividerDecoration(
    context: Context,
    private val marginLeftPx: Int = 0,
    private val marginRightPx: Int = 0,
    private val drawAtIndex: (Int) -> Boolean = { true },
    @DrawableRes private val dividerRes: Int = R.drawable.divider
) : RecyclerView.ItemDecoration() {

    private val marginBounds = Rect()
    private val divider: Drawable = ContextCompat.getDrawable(context, dividerRes)!!

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        canvas.save()

        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft + marginLeftPx
            right = parent.width - parent.paddingRight - marginRightPx
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = marginLeftPx
            right = parent.width - marginRightPx
        }

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val pos = parent.getChildAdapterPosition(child)
            if (pos != RecyclerView.NO_POSITION && drawAtIndex(pos)) {
                parent.getDecoratedBoundsWithMargins(child, marginBounds)
                val bottom: Int = marginBounds.bottom + child.translationY.roundToInt()
                val top = bottom - divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }

        canvas.restore()
    }
}