package cn.yangchengyu.mywanandroid.view

import cn.yangchengyu.mywanandroid.R
import com.chad.library.adapter.base.loadmore.LoadMoreView

/**
 * Desc  : 自定义加载更多View
 * Author: Chengyu Yang
 * Date  : 2019-09-16
 */

class CustomLoadMoreView : LoadMoreView() {
    /**
     * load more layout
     *
     * @return
     */
    override fun getLayoutId(): Int = R.layout.view_load_more

    /**
     * loading view
     *
     * @return
     */
    override fun getLoadingViewId(): Int = R.id.load_more_loading_view

    /**
     * load end view, you can return 0
     *
     * @return
     */
    override fun getLoadEndViewId(): Int = R.id.load_more_load_end_view

    /**
     * load fail view
     *
     * @return
     */
    override fun getLoadFailViewId(): Int = R.id.load_more_load_fail_view

}