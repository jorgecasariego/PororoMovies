package me.jorgecasariego.pororopeliculas.network

import me.jorgecasariego.pororopeliculas.base.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    fun provideMoviewDbApi(retrofit: Retrofit): MovieDbApi {
        return retrofit.create(MovieDbApi::class.java)
    }
}