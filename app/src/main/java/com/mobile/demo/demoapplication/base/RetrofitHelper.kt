package com.mobile.demo.demoapplication.base

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @Description:    描述
 * @Author:         菲克
 * @Modify:         xxx
 */
object RetrofitHelper {
    //baseUrl根据自己项目修改
    private const val BASE_URL = "http://api.dev.al-array.com"

    private var retrofit: Retrofit? = null

    private var retrofitBuilder: Retrofit.Builder? = null

    //Retrofit初始化
    fun init(){
        if (retrofitBuilder == null) {
            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
            retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                //支持Json数据解析
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava返回类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
        }
        retrofit = retrofitBuilder!!.build()
    }

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            throw IllegalAccessException("Retrofit is not initialized!")
        }
        return retrofit!!
    }


}