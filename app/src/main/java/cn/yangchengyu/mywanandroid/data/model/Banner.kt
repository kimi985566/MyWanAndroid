package cn.yangchengyu.mywanandroid.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(var desc: String?,
                  var id: Int?,
                  var imagePath: String?,
                  var isVisible: Int?,
                  var order: Int?,
                  var title: String?,
                  var type: Int?,
                  var url: String?) : Parcelable