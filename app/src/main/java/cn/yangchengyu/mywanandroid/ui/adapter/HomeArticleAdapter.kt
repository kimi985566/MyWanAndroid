package cn.yangchengyu.mywanandroid.ui.adapter

import android.os.Build
import android.text.Html
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.data.model.ArticleBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.apply {
            holder.setText(
                R.id.articleTitle,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(item.title)
                }
            )
            setText(R.id.articleAuthor, item.author)
            setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
            setText(R.id.articleTime, item.niceDate)
        }
    }
}