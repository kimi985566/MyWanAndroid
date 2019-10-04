package cn.yangchengyu.mywanandroid.base

class BaseConstant {
    companion object {
        //服务器地址
        const val SERVER_ADDRESS = "https://www.wanandroid.com"
        //SP表名
        const val TABLE_PREFS = "my_wan_android"
        //Token Key
        const val KEY_SP_TOKEN = "token"
    }

    open class WebViewConstants {
        companion object{
            val WEB_TITLE = "web_title"
            val WEB_URL = "web_url"
        }
    }
}