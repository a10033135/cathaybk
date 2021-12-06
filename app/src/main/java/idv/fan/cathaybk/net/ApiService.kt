package idv.fan.cathaybk.net

import com.google.gson.GsonBuilder
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService private constructor() {

    private val TIME_OUT = 30L

    /* Github API 網址 */
    private val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit

    companion object {
        val instance: ApiService by lazy { ApiService() }
    }

    init {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(TokenHeaderInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    fun <T> getApiService(classofT: Class<T>): T {
        return retrofit.create(classofT)
    }

    /* 使用Github Api 時，需於 Header 加上 token ，３０天需更新一次 */
    class TokenHeaderInterceptor : Interceptor {
        private val GITHUB_OAUTH_TOKEN = "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"
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val headerBuild = Utils.getTokenHeader(GITHUB_OAUTH_TOKEN).toHeaders()
            val requestBuilder = original.newBuilder().headers(headerBuild)
            val request = requestBuilder.build()
            return chain.proceed(request)
        }

    }
}