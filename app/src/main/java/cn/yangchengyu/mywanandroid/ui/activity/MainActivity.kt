package cn.yangchengyu.mywanandroid.ui.activity

import android.content.Intent
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseActivity
import cn.yangchengyu.mywanandroid.ext.onClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initView() {
        text.onClick {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun initData() {}
}
