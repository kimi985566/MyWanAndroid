package cn.yangchengyu.mywanandroid.data.api

import cn.yangchengyu.mywanandroid.data.model.ArticleList
import cn.yangchengyu.mywanandroid.data.model.Banner
import cn.yangchengyu.mywanandroid.data.model.WanResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WanHomeService {

    /**
     * 获取顶部Banner
     * */
    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<Banner>>

    /**
     * 获取首页文章数据
     *
     * @param page 页数
     * */
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    /**
     * 点赞文章
     *
     * @param id 文章Id
     * */
    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): WanResponse<ArticleList>

    /**
     * 取消点赞
     *
     * @param id 文章Id
     * */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): WanResponse<ArticleList>
}