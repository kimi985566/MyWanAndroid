package cn.yangchengyu.mywanandroid.data.repository

import androidx.lifecycle.LifecycleObserver
import cn.yangchengyu.mywanandroid.data.model.WanResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Desc  : BaseRepository
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
open class BaseRepository : LifecycleObserver {
    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return withContext(Dispatchers.IO) { call.invoke() }
    }
}