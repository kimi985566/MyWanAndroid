package cn.yangchengyu.mywanandroid.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Desc  : BaseViewModel
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val exceptionViewModel: MutableLiveData<Throwable> = MutableLiveData()

    fun viewModelLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (exception: Exception) {
                exceptionViewModel.value = exception
                LogUtils.e(this@BaseViewModel.javaClass.simpleName + " " + exception.toString())
            }
        }
    }
}