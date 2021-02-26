package cn.yangchengyu.mywanandroid.ui.activity

import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseViewModelActivity
import cn.yangchengyu.mywanandroid.data.model.LoginSuccess
import cn.yangchengyu.mywanandroid.databinding.ActivityLoginBinding
import cn.yangchengyu.mywanandroid.ext.enable
import cn.yangchengyu.mywanandroid.ext.onClick
import cn.yangchengyu.mywanandroid.ext.safeToString
import cn.yangchengyu.mywanandroid.utils.UserPrefsUtils
import cn.yangchengyu.mywanandroid.viewmodels.LoginViewModel
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity

/**
 * Desc  : 登陆页
 * Author: Chengyu Yang
 * Date  : 2019-09-10
 */

class LoginActivity : BaseViewModelActivity<LoginViewModel>() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var userName: String
    private lateinit var passWord: String

    override fun provideViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initTitle() {
        binding.loginToolBar.toolbar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener { onBackPressed() }
            title = getString(R.string.login)
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun initView() {
        binding.loginButton.enable(binding.loginUserName.editText) { checkInput() }
        binding.loginButton.enable(binding.loginPassWord.editText) { checkInput() }
    }

    override fun initData() {
        binding.loginButton.onClick {
            showProgressDialog()
            viewModel.login(userName, passWord)
        }

        binding.loginRegisterText.onClick {
            startActivity<RegisterActivity>()
        }
    }

    override fun startObserve() {
        super.startObserve()

        viewModel.apply {
            loginUser.observe(this@LoginActivity, {
                //保存用户信息
                UserPrefsUtils.putUserInfo(it)

                ToastUtils.showShort(R.string.loginSuccess)

                EventBus.getDefault().post(LoginSuccess())

                dismissProgressDialog()

                finish()
            })

            errorMsg.observe(this@LoginActivity, {
                dismissProgressDialog()

                it?.run {
                    SnackbarUtils.with(binding.root)
                        .setMessage(it)
                        .setDuration(SnackbarUtils.LENGTH_SHORT)
                        .showError()
                }
            })
        }
    }

    private fun checkInput(): Boolean {
        userName = binding.loginUserName.editText?.text.safeToString()
        passWord = binding.loginPassWord.editText?.text.safeToString()

        if (userName.isEmpty()) {
            binding.loginUserName.error = "请输入用户名"
            return false
        } else {
            binding.loginUserName.error = null
        }

        if (passWord.isEmpty()) {
            binding.loginPassWord.error = "请输入密码"
            return false
        } else {
            binding.loginPassWord.error = null
        }

        return true
    }
}