package cn.yangchengyu.mywanandroid.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import cn.yangchengyu.mywanandroid.data.model.WanResponse
import cn.yangchengyu.mywanandroid.utils.DefaultTextWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Desc  : 扩展方法
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */

/**
 *  扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
 * 扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * 处理数据
 * */
suspend fun executeResponse(
    response: WanResponse<Any>,
    successBlock: suspend CoroutineScope.() -> Unit,
    errorBlock: suspend CoroutineScope.() -> Unit
) {
    coroutineScope {
        when {
            response.errorCode == null || response.errorCode == -1 -> errorBlock()
            else -> successBlock()
        }
    }
}

/**
 * 扩展Button可用性
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@enable.isEnabled = method()
        }
    })
}