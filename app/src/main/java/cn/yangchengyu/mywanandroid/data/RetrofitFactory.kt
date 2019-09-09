package cn.yangchengyu.mywanandroid.data

import cn.yangchengyu.mywanandroid.base.BaseConstant
import cn.yangchengyu.mywanandroid.data.api.WanLoginService
import cn.yangchengyu.mywanandroid.data.intercepter.CacheInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @author Chengyu Yang
 * @date 2018/7/20
 *
 */

object RetrofitFactory {

    private val mRetrofit: Retrofit
    private val mInterceptor: Interceptor

    //登陆服务
    internal val loginService by lazy {
        create(WanLoginService::class.java)
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
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    /**
     * OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
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