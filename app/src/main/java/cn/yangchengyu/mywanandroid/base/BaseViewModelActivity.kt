package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * Desc  : ViewModel Activity
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

abstract class BaseViewModelActivity<V : BaseViewModel> : BaseActivity() {

    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel()
        super.onCreate(savedInstanceState)
        startObserve()
    }

    //提供ViewModel类
    open fun provideViewModelClass(): Class<V>? = null

    private fun initViewModel() {
        provideViewModelClass()?.let {
            viewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    open fun startObserve() {
        viewModel.exceptionViewModel.observe(this, Observer {
            it?.let {
                onError(it)
            }
        })
    }

    open fun onError(e: Throwable) {
        if (progressDialog.isVisible) {
            dismissProgressDialog()
        }

        ToastUtils.showLong("连接出错")

        LogUtils.w(e)
    }

    override fun onDestroy() {
        viewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}