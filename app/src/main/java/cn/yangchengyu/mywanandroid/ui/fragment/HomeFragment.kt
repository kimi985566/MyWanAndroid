package cn.yangchengyu.mywanandroid.ui.fragment

import android.text.TextUtils
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseViewModelFragment
import cn.yangchengyu.mywanandroid.data.model.ArticleList
import cn.yangchengyu.mywanandroid.data.model.Banner
import cn.yangchengyu.mywanandroid.ui.adapter.HomeArticleAdapter
import cn.yangchengyu.mywanandroid.utils.BannerImageLoader
import cn.yangchengyu.mywanandroid.view.CustomLoadMoreView
import cn.yangchengyu.mywanandroid.viewmodels.HomeViewModel
import com.blankj.utilcode.util.SizeUtils
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseViewModelFragment<HomeViewModel>() {

    private val homeArticleAdapter by lazy { HomeArticleAdapter() }

    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()

    private val banner by lazy { com.youth.banner.Banner(activity) }

    private var currentPage = 0

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_home

    override fun providerVMClass(): Class<HomeViewModel>? = HomeViewModel::class.java

    override fun initView() {
        //设置Banner
        initBanner()
        //设置RecyclerView
        initRecyclerView()
        //设置下拉刷新
        initSwiftLayout()
    }

    override fun initData() {
        homeRefreshLayout.isRefreshing = true
        //获取文章数据
        refreshData()
    }

    override fun startObserve() {
        super.startObserve()

        viewModel.apply {
            banners.observe(this@HomeFragment, Observer { bannerList ->
                setBanner(bannerList)
            })

            articleList.observe(this@HomeFragment, Observer { articleList ->
                setArticles(articleList)
            })
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        homeRefreshLayout.isRefreshing = false
    }

    private fun initBanner() {
        banner.run {
            layoutParams =
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(200f))
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setImageLoader(BannerImageLoader())
        }
    }

    private fun initRecyclerView() {
        homeRecycleView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeArticleAdapter.apply {
                //加载更多View
                setLoadMoreView(CustomLoadMoreView())
                //加载更多监听
                setOnLoadMoreListener({ loadMore() }, homeRecycleView)
            }
        }
    }

    private fun initSwiftLayout() {
        homeRefreshLayout.run {
            setOnRefreshListener { refreshData() }
            isRefreshing = true
        }
    }

    private fun refreshData() {
        homeRefreshLayout.isRefreshing = true
        currentPage = 0
        homeArticleAdapter.setEnableLoadMore(false)

        //获取文章数据
        viewModel.getArticleList(currentPage)
        //获取Banner数据
        viewModel.getBanner()
    }

    private fun loadMore() {
        viewModel.getArticleList(currentPage)
    }

    private fun setBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            if (!TextUtils.isEmpty(banner.imagePath) && !TextUtils.isEmpty(banner.title)
                && !TextUtils.isEmpty(banner.url)
            ) {
                bannerImages.add(banner.imagePath!!)
                bannerTitles.add(banner.title!!)
                bannerUrls.add(banner.url!!)
            }
        }

        if (bannerImages.isNotEmpty()) {
            //设置Banner
            banner.apply {
                setImages(bannerImages)
                setBannerTitles(bannerTitles)
                setDelayTime(3000)
                start()
            }

            //加载列表HeaderView
            homeArticleAdapter.addHeaderView(banner)
        }
    }

    private fun setArticles(articleList: ArticleList) {
        homeArticleAdapter.run {
            if (!articleList.datas.isNullOrEmpty()) {
                if (currentPage == 0) {
                    //刷新，首次加载
                    replaceData(articleList.datas!!)
                } else {
                    //加载更多
                    addData(articleList.datas!!)
                }

                //adapter允许加载更多
                setEnableLoadMore(true)
                //加载完成
                loadMoreComplete()

                currentPage++
            }
        }

        homeRefreshLayout.isRefreshing = false
    }
}