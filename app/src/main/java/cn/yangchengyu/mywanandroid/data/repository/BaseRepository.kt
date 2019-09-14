package cn.yangchengyu.mywanandroid.data.repository

import androidx.lifecycle.LifecycleObserver
import cn.yangchengyu.mywanandroid.data.model.WanResponse

/**
 * Desc  : BaseRepository
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
open class BaseRepository : LifecycleObserver {
    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }
}