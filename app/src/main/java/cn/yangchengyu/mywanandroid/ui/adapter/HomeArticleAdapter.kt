package cn.yangchengyu.mywanandroid.ui.adapter

import android.os.Build
import android.text.Html
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.data.model.ArticleBean
import cn.yangchengyu.mywanandroid.utils.ImageLoader
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Desc  : 首页Adapter
 * Author: Chengyu Yang
 * Date  : 2019-09-11
 */
class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.apply {
            //标题
            setText(
                R.id.articleTitle,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(item.title)
                }
            )

            //图片
            if (item.envelopePic.isNullOrEmpty()) {
                setGone(R.id.articleImage, false)
            } else {
                setGone(R.id.articleImage, true)
                ImageLoader.load(
                    mContext,
                    item.envelopePic,
                    itemView.findViewById(R.id.articleImage)
                )
            }

            //作者
            if (item.author.isNullOrEmpty()) {
                setGone(R.id.articleAuthor, false)
            } else {
                setGone(R.id.articleAuthor, true)
                setText(R.id.articleAuthor, item.author)
            }

            //标签
            setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")

            //发布时间
            if (item.niceDate.isNullOrEmpty()) {
                setGone(R.id.articleTimeIcon, false)
            } else {
                setGone(R.id.articleTimeIcon, true)
                setText(R.id.articleTime, item.niceDate)
            }
        }
    }
}