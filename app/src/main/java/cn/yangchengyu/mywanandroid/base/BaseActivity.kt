package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver

/**
 * Desc  : BaseActivity
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)

        //设置布局
        setContentView(getLayoutResId())
        //设置View
        initView()
        //获取数据
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.finishActivity(this)
    }

    //布局Id
    abstract fun getLayoutResId(): Int

    //加载View
    abstract fun initView()

    //加载Data
    abstract fun initData()
}