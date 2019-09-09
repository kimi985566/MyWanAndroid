package cn.yangchengyu.mywanandroid.utils

import android.content.Context
import android.widget.ImageView
import cn.yangchengyu.mywanandroid.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Desc  : ImageLoader
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */

object ImageLoader {

    fun load(context: Context?, url: String?, imageView: ImageView?) {
        context ?: return

        imageView?.apply {
            Glide.with(context).clear(imageView)

            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.bg_placeholder)
                        .error(R.mipmap.bg_pic_load_error)
                )
                .into(this)
        }
    }
}