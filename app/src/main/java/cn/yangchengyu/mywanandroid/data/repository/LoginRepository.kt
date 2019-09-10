package cn.yangchengyu.mywanandroid.data.repository

import cn.yangchengyu.mywanandroid.data.RetrofitFactory
import cn.yangchengyu.mywanandroid.data.api.BaseRepository
import cn.yangchengyu.mywanandroid.data.model.UserInfo
import cn.yangchengyu.mywanandroid.data.model.WanResponse

/**
 * Desc  : 登陆Repository
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
class LoginRepository : BaseRepository() {

    //登陆
    suspend fun login(userName: String, passWord: String): WanResponse<UserInfo> {
        return apiCall {
            RetrofitFactory.loginService.login(userName, passWord)
        }
    }

    //注册
    suspend fun register(userName: String, passWord: String): WanResponse<UserInfo> {
        return apiCall {
            RetrofitFactory.loginService.register(userName, passWord, passWord)
        }
    }
}