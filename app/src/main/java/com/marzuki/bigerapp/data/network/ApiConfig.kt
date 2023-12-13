package com.marzuki.bigerapp.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val BASE_URL = "https://capstone-ch2-ps514.et.r.appspot.com/api/v1/"

    fun getApiServiceWithToken(token: String): ApiService {
        val client = createOkHttpClient(token)
        return createRetrofit(client).create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        val client = createOkHttpClient()
        return createRetrofit(client).create(ApiService::class.java)
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
