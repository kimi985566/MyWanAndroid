package cn.yangchengyu.mywanandroid.base

import android.app.Activity
import java.util.*

object AppManager {

    private val activityStack: Stack<Activity> = Stack()


    /**
     * 入栈
     * */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 出栈
     * */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /**
     * 获取当前栈顶
     * */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     * 清理栈
     * */
    private fun finishAllActivity() {
        activityStack.forEach { activity ->
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     * */
    fun exitApp() {
        finishAllActivity()
    }
}