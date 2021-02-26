package cn.yangchengyu.mywanandroid.ui.activity

import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseViewModelActivity
import cn.yangchengyu.mywanandroid.databinding.ActivityRegisterBinding
import cn.yangchengyu.mywanandroid.ext.enable
import cn.yangchengyu.mywanandroid.ext.onClick
import cn.yangchengyu.mywanandroid.viewmodels.LoginViewModel
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

/**
 * Desc  : 注册页面
 * Author: Chengyu Yang
 * Date  : 2019-09-11
 */
class RegisterActivity : BaseViewModelActivity<LoginViewModel>() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var userName: String
    private lateinit var passWord: String

    override fun provideViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initBinding() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initTitle() {
        binding.registerToolBar.toolbar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener { onBackPressed() }
            title = getString(R.string.register)
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun initView() {
        binding.registerButton.enable(binding.registerUserName.editText) { checkInput() }
        binding.registerButton.enable(binding.registerPassWord.editText) { checkInput() }
    }

    override fun initData() {
        binding.registerButton.onClick {
            showProgressDialog()
            viewModel.register(userName, passWord)
        }
    }

    override fun startObserve() {
        super.startObserve()

        viewModel.apply {
            registerUser.observe(this@RegisterActivity, {
                dismissProgressDialog()

                ToastUtils.showShort(R.string.registerSuccess)

                //启动登陆页
                startActivity(intentFor<LoginActivity>().singleTop().clearTop())
            })

            errorMsg.observe(this@RegisterActivity, {
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
        userName = binding.registerUserName.editText?.text.toString()
        passWord = binding.registerPassWord.editText?.text.toString()

        if (userName.isEmpty()) {
            binding.registerUserName.error = "请输入用户名"
            return false
        } else {
            binding.registerUserName.error = null
        }

        if (passWord.isEmpty()) {
            binding.registerPassWord.error = "请输入密码"
            return false
        } else {
            binding.registerPassWord.error = null
        }

        return true
    }
}