package cn.yangchengyu.mywanandroid.data.repository

import cn.yangchengyu.mywanandroid.data.RetrofitFactory
import cn.yangchengyu.mywanandroid.data.model.ArticleList
import cn.yangchengyu.mywanandroid.data.model.Banner
import cn.yangchengyu.mywanandroid.data.model.WanResponse

class HomeRepository : BaseRepository() {

    /**
     * 获取Banner
     * */
    suspend fun getBanner(): WanResponse<List<Banner>> {
        return apiCall {
            RetrofitFactory.homeService.getBanner()
        }
    }

    /**
     * 获取首页文章
     *
     * @param page 页数
     * */
    suspend fun getArticleList(page: Int): WanResponse<ArticleList> {
        return apiCall {
            RetrofitFactory.homeService.getHomeArticles(page)
        }
    }

    /**
     * 收藏文章
     *
     * @param articleId 文章id
     * */
    suspend fun collectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall {
            RetrofitFactory.homeService.collectArticle(articleId)
        }
    }

    /**
     * 取消收藏
     *
     * @param articleId 文章id
     * */
    suspend fun unCollectArticle(articleId: Int): WanResponse<ArticleList> {
        return apiCall {
            RetrofitFactory.homeService.cancelCollectArticle(articleId)
        }
    }
}