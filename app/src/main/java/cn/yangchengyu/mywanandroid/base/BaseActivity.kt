package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import cn.yangchengyu.mywanandroid.ui.fragment.ProgressDialogFragment

/**
 * Desc  : BaseActivity
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    private val progressDialog: ProgressDialogFragment by lazy {
        ProgressDialogFragment.newInstance(
            "加载中"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.addActivity(this)

        //设置布局
        setContentView(getLayoutResId())
        //设置标题
        initTitle()
        //设置View
        initView()
        //获取数据
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.finishActivity(this)
    }

    fun showProgressDialog() {
        progressDialog.show(supportFragmentManager, "loadingDialog")
    }

    fun dismissProgressDialog() {
        progressDialog.dismissAllowingStateLoss()
    }

    //布局Id
    abstract fun getLayoutResId(): Int

    //设置标题
    abstract fun initTitle()

    //加载View
    abstract fun initView()

    //加载Data
    abstract fun initData()
}