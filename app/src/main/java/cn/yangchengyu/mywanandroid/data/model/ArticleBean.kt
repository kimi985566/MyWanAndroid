package cn.yangchengyu.mywanandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleBean(
    var apkLink: String?,
    var author: String?,
    var chapterId: Int?,
    var chapterName: String?,
    var collect: Boolean?,
    var courseId: Int?,
    var desc: String?,
    var envelopePic: String?,
    var fresh: Boolean?,
    var id: Int?,
    var link: String?,
    var niceDate: String?,
    var origin: String?,
    var prefix: String?,
    var projectLink: String?,
    var publishTime: Long?,
    var superChapterId: Int?,
    var superChapterName: String?,
    var tags: List<Tag>?,
    var title: String?,
    var type: Int?,
    var userId: Int?,
    var visible: Int?,
    var zan: Int?
) : Parcelable

@Parcelize
data class Tag(
    var name: String,
    var url: String
) : Parcelable