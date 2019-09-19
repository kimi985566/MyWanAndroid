package cn.yangchengyu.mywanandroid.viewmodels

import androidx.lifecycle.MutableLiveData
import cn.yangchengyu.mywanandroid.base.BaseViewModel
import cn.yangchengyu.mywanandroid.data.model.ArticleList
import cn.yangchengyu.mywanandroid.data.model.Banner
import cn.yangchengyu.mywanandroid.data.repository.HomeRepository
import cn.yangchengyu.mywanandroid.ext.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    val articleList: MutableLiveData<ArticleList> by lazy { MutableLiveData<ArticleList>() }
    val banners: MutableLiveData<List<Banner>> by lazy { MutableLiveData<List<Banner>>() }
    val errorMsg: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    private val repository by lazy { HomeRepository() }

    fun getBanner() {
        viewModelLaunch {
            val bannerResponse = withContext(Dispatchers.IO) {
                repository.getBanner()
            }

            executeResponse(
                bannerResponse,
                { banners.postValue(bannerResponse.data) },
                { errorMsg.postValue(bannerResponse.errorMsg) }
            )
        }
    }

    fun getArticleList(page: Int) {
        viewModelLaunch {
            val articleResponse = withContext(Dispatchers.IO) {
                repository.getArticleList(page)
            }

            executeResponse(
                articleResponse,
                { articleList.postValue(articleResponse.data) },
                { errorMsg.postValue(articleResponse.errorMsg) }
            )
        }
    }

    fun collectArticle(articleId: Int, boolean: Boolean) {
        viewModelLaunch {
            withContext(Dispatchers.IO) {
                when {
                    boolean -> repository.collectArticle(articleId)
                    else -> repository.unCollectArticle(articleId)
                }
            }
        }
    }
}
