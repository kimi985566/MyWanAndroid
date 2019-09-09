package cn.yangchengyu.mywanandroid.ext

import android.view.View

/**
 * Desc  : 扩展方法
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */

/*
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}