package cn.yangchengyu.mywanandroid.ui.activity

import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseActivity
import cn.yangchengyu.mywanandroid.ext.onClick
import cn.yangchengyu.mywanandroid.utils.UserPrefsUtils
import com.blankj.utilcode.util.SnackbarUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initTitle() {
        setSupportActionBar(mainToolBar.toolbar)
    }

    override fun initView() {
        //设置DrawerLayout
        initDrawerLayout()

        text.onClick {
            if (UserPrefsUtils.isLogined()) {
                SnackbarUtils.with(text)
                    .setMessage("已登录")
                    .showSuccess()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    /**
     * 设置DrawerLayout
     * */
    private fun initDrawerLayout() {
        drawerLayout.apply {
            val toggle = ActionBarDrawerToggle(
                this@MainActivity,
                this,
                mainToolBar.toolbar,
                R.string.app_name,
                R.string.app_name
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    override fun initData() {}
}
