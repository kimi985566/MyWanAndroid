package cn.yangchengyu.mywanandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleList(
    var offset: Int?,
    var size: Int?,
    var total: Int?,
    var pageCount: Int?,
    var curPage: Int?,
    var over: Boolean?,
    var datas: List<ArticleBean>?
) : Parcelable