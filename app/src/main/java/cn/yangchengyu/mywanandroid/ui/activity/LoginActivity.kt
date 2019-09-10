package cn.yangchengyu.mywanandroid.ui.activity

import androidx.lifecycle.Observer
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseViewModelActivity
import cn.yangchengyu.mywanandroid.ext.enable
import cn.yangchengyu.mywanandroid.ext.onClick
import cn.yangchengyu.mywanandroid.viewmodels.LoginViewModel
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Desc  : 登陆页
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

class LoginActivity : BaseViewModelActivity<LoginViewModel>() {

    private lateinit var userName: String
    private lateinit var passWord: String

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun provideViewModelClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {
        loginToolBar.title = "登陆"
        loginToolBar.setNavigationIcon(R.drawable.arrow_back)
        loginToolBar.setNavigationOnClickListener { onBackPressed() }

        loginButton.enable(loginUserName.editText!!) { checkInput() }
        loginButton.enable(loginPassWord.editText!!) { checkInput() }
    }

    override fun initData() {
        loginButton.onClick {
            showProgressDialog()
            viewModel.login(userName, passWord)
        }

        loginRegisterText.onClick {
            ToastUtils.showShort("注册用户")
            this.finish()
        }
    }

    override fun startObserve() {
        viewModel.apply {
            loginUser.observe(this@LoginActivity, Observer {
                dismissProgressDialog()
            })

            errorMsg.observe(this@LoginActivity, Observer {
                dismissProgressDialog()

                it?.run {
                    SnackbarUtils.with(loginButton)
                        .setMessage(it)
                        .setDuration(SnackbarUtils.LENGTH_SHORT)
                        .showError()
                }
            })
        }
    }

    private fun checkInput(): Boolean {
        userName = loginUserName.editText?.text.toString()
        passWord = loginPassWord.editText?.text.toString()

        if (userName.isEmpty()) {
            loginUserName.error = "请输入用户名"
            return false
        } else {
            loginUserName.error = null
        }

        if (passWord.isEmpty()) {
            loginPassWord.error = "请输入密码"
            return false
        } else {
            loginPassWord.error = null
        }

        return true
    }
}