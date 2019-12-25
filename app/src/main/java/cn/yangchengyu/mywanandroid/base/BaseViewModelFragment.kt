package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

abstract class BaseViewModelFragment<V : BaseViewModel> : BaseFragment() {

    lateinit var viewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVM()
        initView()
        initData()
        startObserve()
    }

    open fun startObserve() {
        viewModel.exceptionViewModel.observe(this, Observer { t ->
            onError(t)
        })
    }

    open fun onError(e: Throwable) {
        if (progressDialog.isVisible) {
            dismissProgressDialog()
        }

        ToastUtils.showLong("连接出错")

        LogUtils.w(e)
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let { cls ->
            activity?.run {
                viewModel = ViewModelProviders.of(this).get(cls)
                lifecycle.addObserver(viewModel)
            } ?: throw Exception("Invalid Activity")
        }
    }

    open fun providerVMClass(): Class<V>? = null

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }
}