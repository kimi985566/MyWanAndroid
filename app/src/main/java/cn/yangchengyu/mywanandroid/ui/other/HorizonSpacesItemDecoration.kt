package com.example.ycy.baselibrary.ui.other

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

/**
 * Desc  : 横向间距
 * Author: Chengyu Yang
 * Date  : 2018/12/6
 */
class HorizonSpacesItemDecoration constructor(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = SizeUtils.dp2px(space.toFloat())
        outRect.right = SizeUtils.dp2px(space.toFloat())
    }
}