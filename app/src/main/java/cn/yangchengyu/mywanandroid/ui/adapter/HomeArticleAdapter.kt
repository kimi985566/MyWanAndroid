package cn.yangchengyu.mywanandroid.ui.adapter

import android.os.Build
import android.text.Html
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.data.model.ArticleBean
import cn.yangchengyu.mywanandroid.utils.ImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * Desc  : 首页Adapter
 * Author: Chengyu Yang
 * Date  : 2019-09-11
 */
class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.apply {
            setText(
                R.id.articleTitle,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(item.title)
                }
            )
            setText(R.id.articleAuthor, item.author)
            if (item.envelopePic.isNullOrBlank()) {
                setGone(R.id.articleImage, false)
            } else {
                setGone(R.id.articleImage, true)
                ImageLoader.load(mContext, item.envelopePic, itemView.articleImage)
            }
            setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
            setText(R.id.articleTime, item.niceDate)
        }
    }
}