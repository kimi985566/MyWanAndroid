package cn.yangchengyu.mywanandroid.data

import cn.yangchengyu.mywanandroid.WanApplication
import cn.yangchengyu.mywanandroid.base.BaseConstant
import cn.yangchengyu.mywanandroid.data.api.WanHomeService
import cn.yangchengyu.mywanandroid.data.api.WanLoginService
import cn.yangchengyu.mywanandroid.data.intercepter.CacheInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * @author Chengyu Yang
 * @date 2019/9/9
 */

object RetrofitFactory {

    private val mRetrofit: Retrofit
    private val mInterceptor: Interceptor

    //cookie持久化
    private val cookieJar by lazy {
        PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(WanApplication.context)
        )
    }

    //登陆服务
    internal val loginService by lazy {
        create(WanLoginService::class.java)
    }

    //首页服务
    internal val homeService by lazy {
        create(WanHomeService::class.java)
    }

    init {
        //通用拦截
        mInterceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .build()

            chain.proceed(request)
        }

        mRetrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(initClient())
                .build()
    }

    /**
     * OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(initLogInterceptor())
                .addInterceptor(CacheInterceptor())
                .addInterceptor(mInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    /**
     * 日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
     *具体服务实例化
     */
    private fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}