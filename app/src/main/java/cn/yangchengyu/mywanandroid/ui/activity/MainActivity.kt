package cn.yangchengyu.mywanandroid.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.AppManager
import cn.yangchengyu.mywanandroid.base.BaseActivity
import cn.yangchengyu.mywanandroid.base.ProviderConstant
import cn.yangchengyu.mywanandroid.data.model.LoginSuccess
import cn.yangchengyu.mywanandroid.data.repository.LoginRepository
import cn.yangchengyu.mywanandroid.ext.executeResponse
import cn.yangchengyu.mywanandroid.ext.onClick
import cn.yangchengyu.mywanandroid.ext.tryCatchLaunch
import cn.yangchengyu.mywanandroid.ui.fragment.HomeFragment
import cn.yangchengyu.mywanandroid.utils.AppPrefsUtils
import cn.yangchengyu.mywanandroid.utils.ImageLoader
import cn.yangchengyu.mywanandroid.utils.UserPrefsUtils
import com.blankj.utilcode.util.SnackbarUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*
import kotlinx.android.synthetic.main.view_nav_header_main.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Desc  : 首页
 * Author: Chengyu Yang
 * Date  : 2019-09-11
 */

class MainActivity : BaseActivity() {

    private var pressTime: Long = 0

    private var tabIndex = 0

    private val homeFragment by lazy { HomeFragment.newInstance() }

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initTitle() {
        mainToolBar?.toolbar?.apply {
            setSupportActionBar(this)
        }
    }

    override fun initView() {
        //设置DrawerLayout
        initDrawerLayout()
        //设置底部导航
        initBottomNavigation()

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, homeFragment)
            .commit()
    }

    override fun initData() {
        //设置NavigationView
        initNavigationViewLogState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_main_scan -> {
                toast(R.string.toolbar_main_scan)
                return true
            }
            R.id.toolbar_main_search -> {
                toast(R.string.toolbar_main_search)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        } else {
            if (KeyEvent.KEYCODE_BACK == keyCode) {
                val time = System.currentTimeMillis()
                when {
                    time - pressTime > 2000 -> {
                        pressTime = time
                        SnackbarUtils.with(navigationView)
                            .setMessage(getString(R.string.pressAgain))
                            .showWarning()
                        return true
                    }
                    else -> AppManager.exitApp()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(loginSuccess: LoginSuccess) {
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 设置DrawerLayout
     * */
    private fun initDrawerLayout() {
        //设置Drawer 显示hamburger Icon
        initDrawer()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_collect -> {

                }
                R.id.nav_about -> {

                }
                R.id.nav_setting -> {

                }
                R.id.nav_logout -> {
                    logout()
                }
                R.id.nav_todo_list -> {

                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            false
        }
    }

    private fun initDrawer() {
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

    private fun initDrawerHeaderView() {
        when {
            UserPrefsUtils.isLogined() -> navigationView.getHeaderView(0)?.apply {
                navHeaderIcon.visibility = View.VISIBLE
                ImageLoader.load(
                    this@MainActivity,
                    AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON),
                    this.navHeaderIcon
                )

                navHeaderUserName.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)

                onClick {
                    return@onClick
                }
            }
            else -> navigationView.getHeaderView(0)?.apply {
                navHeaderIcon.visibility = View.GONE

                navHeaderUserName.text = "请登录"

                onClick {
                    startActivity<LoginActivity>()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        }
    }

    /**
     * 设置底部导航
     * */
    private fun initBottomNavigation() {
        mainNavigationView.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainContainer, homeFragment)
                            .commit()
                    }
                    R.id.navigation_wechat -> {
                        tabIndex = 1
                        toast(getString(R.string.navigation_wechat))
                    }
                    R.id.navigation_project -> {
                        tabIndex = 2
                        toast(getString(R.string.navigation_project))
                    }
                    R.id.navigation_navigation -> {
                        tabIndex = 3
                        toast(getString(R.string.navigation_navigation))
                    }
                    R.id.navigation_knowledge_tree -> {
                        tabIndex = 4
                        toast(getString(R.string.navigation_knowledge_tree))
                    }
                }
                showFragmentByIndex(tabIndex)
                true
            }
        }
    }

    private fun showFragmentByIndex(tabIndex: Int) {

    }

    /**
     * 登出
     * */
    private fun logout() {
        AlertDialog.Builder(this)
            .setMessage("是否确认退出登录？")
            .setPositiveButton("确定") { _, _ ->
                tryCatchLaunch({
                    executeResponse(
                        //logout response
                        LoginRepository().logout(),
                        //success
                        {
                            //登出
                            UserPrefsUtils.putUserInfo(null)
                            //设置Drawer状态
                            initNavigationViewLogState()
                            //登出
                            SnackbarUtils.with(mainNavigationView)
                                .setMessage("登出成功")
                                .showSuccess()
                        },
                        //error
                        {
                            SnackbarUtils.with(mainNavigationView)
                                .setMessage("登出失败")
                                .showWarning()
                        }
                    )
                }, {
                    SnackbarUtils.with(mainNavigationView)
                        .setMessage("登出错误")
                        .showError()
                })
            }
            .setNegativeButton("取消") { _, _ ->

            }
            .create()
            .show()
    }

    private fun initNavigationViewLogState() {
        //是否显示登出
        navigationView.menu.findItem(R.id.nav_logout).isVisible = UserPrefsUtils.isLogined()
        //设置头部显示
        initDrawerHeaderView()
    }
}
