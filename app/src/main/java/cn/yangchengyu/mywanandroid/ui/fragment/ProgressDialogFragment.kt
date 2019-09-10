package cn.yangchengyu.mywanandroid.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import cn.yangchengyu.mywanandroid.R


/**
 * Desc  : 通用加载动画
 * Author: Chengyu Yang
 * Date  : 2018/11/26
 */
class ProgressDialogFragment : DialogFragment() {

    /**
     * 加载动画ImageView
     * */
    private lateinit var imageView: ImageView
    /**
     * 加载提示
     * */
    private lateinit var textView: TextView

    private var loadingText: String? = null

    private var animatedVectorDrawableCompat: AnimatedVectorDrawableCompat? = null

    companion object {
        private const val LOADING_TEXT = "loading_text"

        @JvmStatic
        fun newInstance(loadingText: String) = ProgressDialogFragment().apply {
            arguments = Bundle().apply {
                putString(LOADING_TEXT, loadingText)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Dialog的style
        setStyle(STYLE_NO_TITLE, R.style.LightProgressDialog)

        arguments?.let {
            loadingText = it.getString(LOADING_TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Dialog的布局样式
        return inflater.inflate(R.layout.progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.iv_loading)
        textView = view.findViewById(R.id.tv_loading)

        when {
            !TextUtils.isEmpty(loadingText) -> {
                textView.visibility = View.VISIBLE
                textView.text = loadingText
            }
            else -> textView.visibility = View.GONE
        }

        animatedVectorDrawableCompat =
            AnimatedVectorDrawableCompat.create(context!!, R.drawable.animated_splash_logo)

        imageView.apply {
            setImageDrawable(animatedVectorDrawableCompat)
        }

        animatedVectorDrawableCompat?.start()
    }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        val window = dialog?.window
        val params = window!!.attributes

        //Dialog居中显示
        params.gravity = Gravity.CENTER
        //Dialog背景透明度
        params.dimAmount = 0.2F

        window.attributes = params
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commitAllowingStateLoss()
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            //如果在Activity保存完状态（onSaveInstanceState）后再添加Fragment会出错
            //Can not perform this action after onSaveInstanceState
            //因此需要使用commitAllowingStateLoss()，在dismiss时也需要使用dismissAllowingStateLoss()
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
            //同一实例使用不同的tag会异常
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        dialog?.apply {
            setDismissMessage(null)
        }
    }
}
