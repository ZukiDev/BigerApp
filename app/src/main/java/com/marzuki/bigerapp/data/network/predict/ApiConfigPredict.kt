package com.marzuki.bigerapp.data.network.predict

import com.marzuki.bigerapp.data.network.post.ApiServicePost
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigPredict {

    private const val BASE_URL = "https://bs-rec4-awzaea3bwq-et.a.run.app/v1/"

    fun getApiServiceWithToken(token: String): ApiServicePredict {
        val client = createOkHttpClient(token)
        return createRetrofit(client).create(ApiServicePredict::class.java)
    }

    fun getApiService(): ApiServicePredict {
        val client = createOkHttpClient()
        return createRetrofit(client).create(ApiServicePredict::class.java)
    }

    private fun createOkHttpClient(token: String? = null): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

        token?.let {
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(requestHeaders)
            }
            clientBuilder.addInterceptor(authInterceptor)
        }

        return clientBuilder.build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}