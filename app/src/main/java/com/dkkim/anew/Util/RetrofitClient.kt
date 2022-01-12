package com.dkkim.anew.Fragment

import com.dkkim.anew.Util.RetrofitService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RetrofitClient {
    companion object{

        private final val baseUrl: String = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/"

        //인터셉터 추가
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        //클라이언트 생성
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()


        var gson = GsonBuilder().setLenient().create()

        fun create(): RetrofitService {
            val retrofit by lazy {
                Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit.create(RetrofitService::class.java)
        }


    }
}