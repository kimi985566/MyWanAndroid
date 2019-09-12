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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

/**
 * Desc  : 注册页面
 * Author: Chengyu Yang
 * Date  : 2019-09-11
 */
class RegisterActivity : BaseViewModelActivity<LoginViewModel>() {

    private lateinit var userName: String
    private lateinit var passWord: String

    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun provideViewModelClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initTitle() {
        setSupportActionBar(registerToolBar.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        registerToolBar.toolbar.setNavigationOnClickListener { onBackPressed() }
        registerToolBar.toolbar.title = getString(R.string.register)
    }

    override fun initView() {
        registerButton.enable(registerUserName.editText!!) { checkInput() }
        registerButton.enable(registerPassWord.editText!!) { checkInput() }
    }

    override fun initData() {
        registerButton.onClick {
            showProgressDialog()
            viewModel.register(userName, passWord)
        }
    }

    override fun startObserve() {
        super.startObserve()

        viewModel.apply {
            registerUser.observe(this@RegisterActivity, Observer {
                dismissProgressDialog()

                ToastUtils.showShort(R.string.registerSuccess)

                //启动登陆页
                startActivity(intentFor<LoginActivity>().singleTop().clearTop())
            })

            errorMsg.observe(this@RegisterActivity, Observer {
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
        userName = registerUserName.editText?.text.toString()
        passWord = registerPassWord.editText?.text.toString()

        if (userName.isEmpty()) {
            registerUserName.error = "请输入用户名"
            return false
        } else {
            registerUserName.error = null
        }

        if (passWord.isEmpty()) {
            registerPassWord.error = "请输入密码"
            return false
        } else {
            registerPassWord.error = null
        }

        return true
    }
}