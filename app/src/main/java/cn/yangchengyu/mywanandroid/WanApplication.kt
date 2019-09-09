package cn.yangchengyu.mywanandroid

import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import kotlin.properties.Delegates

/**
 * Desc  : MyWanAndroid Application
 * Author: Chengyu Yang
 * Date  : 2019-09-09
 */

class WanApplication : MultiDexApplication() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        //ARouter
        initARouter()
        //Android Util
        initUtils()

        //lifeCycleCallBacks
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    private fun initARouter() {
        if (isDebug(this)) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initUtils() {
        Utils.init(this)
    }

    private val lifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            LogUtils.d(activity.componentName.className + "onCreated")
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            LogUtils.d(activity.componentName.className + "onResume")
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            LogUtils.d(activity.componentName.className + "onDestroy")
        }
    }

    private fun isDebug(context: Context): Boolean {
        return context.applicationInfo != null && (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE !== 0)
    }
}