package cn.yangchengyu.mywanandroid.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import cn.yangchengyu.mywanandroid.ui.fragment.ProgressDialogFragment

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2019-09-16
 */

open class BaseFragment : Fragment, LifecycleObserver {

    constructor() : super()

    constructor(@LayoutRes resId: Int) : super(resId)

    protected val progressDialog: ProgressDialogFragment by lazy {
        ProgressDialogFragment.newInstance(
            "加载中"
        )
    }

    fun showProgressDialog() {
        progressDialog.show(childFragmentManager, "loadingDialog")
    }

    fun dismissProgressDialog() {
        progressDialog.dismissAllowingStateLoss()
    }
}