package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils

abstract class BaseViewModelFragment<V : BaseViewModel> : BaseFragment {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    lateinit var viewModel: V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVM()
        initView()
        initData()
        startObserve()
    }

    open fun startObserve() {
        viewModel.exceptionViewModel.observe(viewLifecycleOwner, { t ->
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

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let { cls ->
            activity?.run {
                viewModel = ViewModelProvider(this).get(cls)
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