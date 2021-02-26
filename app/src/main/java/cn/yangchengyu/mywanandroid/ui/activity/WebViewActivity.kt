package cn.yangchengyu.mywanandroid.ui.activity

import android.graphics.Bitmap
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import cn.yangchengyu.mywanandroid.R
import cn.yangchengyu.mywanandroid.base.BaseActivity
import cn.yangchengyu.mywanandroid.base.BaseConstant
import cn.yangchengyu.mywanandroid.databinding.ActivityWebviewBinding
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.*

/**
 * Desc  : WebView页面
 * Author: Chengyu Yang
 * Date  : 2019-10-04
 */

class WebViewActivity : BaseActivity() {

    private lateinit var binding: ActivityWebviewBinding

    private lateinit var webTitle: String
    private lateinit var webUrl: String

    private lateinit var layoutParams: CoordinatorLayout.LayoutParams

    private var agentWeb: AgentWeb? = null
    private val agentWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override fun initBinding() {
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initTitle() {
        binding.webViewToolBar.toolbar.apply {
            setSupportActionBar(this)
            setNavigationOnClickListener { finish() }
        }

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun initView() {
        layoutParams =
            CoordinatorLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }
    }

    override fun initData() {
        intent?.extras?.let {
            webTitle = it.getString(
                BaseConstant.WebViewConstants.WEB_TITLE,
                getString(R.string.webview_title_err)
            )
            webUrl = it.getString(
                BaseConstant.WebViewConstants.WEB_URL,
                ""
            )
        }

        agentWeb = AgentWeb.with(this)
            .setAgentWebParent(binding.webViewContent, layoutParams)
            .useDefaultIndicator()
            .setWebView(agentWebView)
            .setWebChromeClient(webChromeClient)
            .setWebViewClient(webViewClient)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            .go(webUrl)

        agentWeb?.webCreator?.webView?.run {
            settings.domStorageEnabled = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    override fun onBackPressed() {
        agentWeb?.let {
            if (!it.back()) {
                finish()
            }
        }
        super.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            binding.webViewToolBar.toolbar.title = title
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.webViewToolBar.toolbar.title = getString(R.string.webview_loading)
        }
    }
}