package cn.yangchengyu.mywanandroid.ext

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cn.yangchengyu.mywanandroid.data.model.WanResponse
import cn.yangchengyu.mywanandroid.utils.DefaultTextWatcher
import cn.yangchengyu.mywanandroid.utils.FragmentViewBindingDelegate
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

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
        when (response.errorCode) {
            null, -1 -> errorBlock()
            else -> successBlock()
        }
    }
}

/**
 * 扩展Button可用性
 */
fun Button.enable(et: EditText?, method: () -> Boolean) {
    et?.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@enable.isEnabled = method()
        }
    })
}

/**
 * try catch的协程
 * */
fun tryCatchLaunch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.() -> Unit
) {
    runBlocking {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                LogUtils.e(this.coroutineContext.javaClass.simpleName + " " + e.toString())
                catchBlock()
            }
        }
    }
}

fun CharSequence?.safeToString() = this?.toString() ?: ""

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy {
        bindingInflater.invoke(layoutInflater)
    }