package cn.yangchengyu.mywanandroid.data.api

import cn.yangchengyu.mywanandroid.data.model.UserInfo
import cn.yangchengyu.mywanandroid.data.model.WanResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Desc  : WanLoginService
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
interface WanLoginService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") userName: String,
        @Field("password") passWord: String
    ): WanResponse<UserInfo>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") rePassWord: String
    ): WanResponse<UserInfo>

    @GET("/user/logout/json")
    suspend fun logout(): WanResponse<Any>
}