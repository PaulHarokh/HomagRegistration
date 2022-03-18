package by.paulharokh.testhomag.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://gg-web.ru/api/"

interface ApiRequest {

    @POST("reg.php")
    suspend fun postReg(@Body regBody:Reg): RegAnswer

    @POST("auth.php")
    suspend fun postAuth(@Body authBody:Auth): AuthAnswer

    companion object Factory {
        fun create(): ApiRequest {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiRequest::class.java)
        }
    }

}
