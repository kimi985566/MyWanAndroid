package cn.yangchengyu.mywanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseViewModelFragment<V : BaseViewModel> : Fragment() {

    lateinit var viewModel: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun startObserve() {
        viewModel.exceptionViewModel.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let {
            viewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(viewModel)
        }
    }

    open fun providerVMClass(): Class<V>? = null

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }
}