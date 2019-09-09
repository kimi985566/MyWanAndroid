package cn.yangchengyu.mywanandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Desc  : UserInfo
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
@Parcelize
data class UserInfo(
    var collectIds: List<Int>? = null,
    var email: String? = null,
    var icon: String? = null,
    var id: Int? = null,
    var password: String? = null,
    var type: Int? = null,
    var username: String? = null
) : Parcelable