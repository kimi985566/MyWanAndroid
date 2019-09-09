package cn.yangchengyu.mywanandroid.data.model


/**
 * Desc  : WanResponse
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
data class WanResponse<out T>(
    val errorCode: Int? = null,
    val errorMsg: String? = null,
    val data: T? = null
)