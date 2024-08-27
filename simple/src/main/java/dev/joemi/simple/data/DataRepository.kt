package dev.joemi.simple.data

import android.text.TextUtils
import com.blankj.utilcode.util.GsonUtils
import dev.joemi.arch.data.DataResult
import dev.joemi.simple.BuildConfig
import dev.joemi.simple.data.bean.HotSearch
import dev.joemi.simple.data.remote.ApiService
import dev.joemi.simple.data.remote.HttpLogger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataRepository {

    private val retrofitService: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .header("User-Agent", "Android/v" + BuildConfig.VERSION_NAME)

                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })

        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor(HttpLogger())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logInterceptor)
        }
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    private val apiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        retrofitService.create(ApiService::class.java)
    }

    suspend fun getBiliBiliRS(): DataResult<List<HotSearch>?> {
        val response = apiService.getBiliBiliRS()
        if (!response.isSuccessful || response.body() == null) {
            return DataResult.createWithFailure("http error, code: ${response.code()}")
        }
        val responseBody = response.body()
        val jsonObj = JSONObject(responseBody!!.string())
        val code = jsonObj.getInt("code")
        if (code != 1) {
            return DataResult.createWithFailure("api error, code: $code")
        }
        val dataArr = jsonObj.getJSONArray("data")
        val list = GsonUtils.fromJson(dataArr.toString(), Array<HotSearch>::class.java).toList()
        return DataResult.createWithSuccess(list)
    }

}