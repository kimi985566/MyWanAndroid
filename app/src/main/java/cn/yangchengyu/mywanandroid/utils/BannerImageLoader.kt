package cn.yangchengyu.mywanandroid.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Desc  : BannerImageLoader
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */
class BannerImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}