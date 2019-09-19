package com.example.ycy.baselibrary.ui.other

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * Desc  : 纵列间距
 * Author: Chengyu Yang
 * Date  : 2018/12/7
 */

class VerticalSpacesItemDecoration constructor(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = SizeUtils.dp2px(0f)
        outRect.bottom = SizeUtils.dp2px(space.toFloat())
    }
}