package idv.fan.cathaybk.net

import com.google.gson.GsonBuilder
import idv.fan.cathaybk.Utils
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
    private val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit

    companion object {
        val TAG = ApiService::class.java.simpleName
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