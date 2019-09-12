package cn.yangchengyu.mywanandroid.utils

import cn.yangchengyu.mywanandroid.base.BaseConstant
import cn.yangchengyu.mywanandroid.base.ProviderConstant
import cn.yangchengyu.mywanandroid.data.model.UserInfo

object UserPrefsUtils {
    /*
      退出登录时，传入null,清空存储
   */
    fun putUserInfo(userInfo: UserInfo?) {
        AppPrefsUtils.putInt(BaseConstant.KEY_SP_TOKEN, userInfo?.id ?: -1)
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_ICON, userInfo?.icon ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_NAME, userInfo?.username ?: "")
        AppPrefsUtils.putString(ProviderConstant.KEY_SP_USER_EMAIL, userInfo?.email ?: "")
    }

    fun isLogined(): Boolean {
        return AppPrefsUtils.getInt(BaseConstant.KEY_SP_TOKEN) != -1
    }
}