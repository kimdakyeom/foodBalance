package com.dkkim.anew.Fragment

import com.dkkim.anew.Util.RetrofitService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class RetrofitClient {
    companion object {

        // 공공데이터 open api baseUrl
        private final val baseUrl: String =
            "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/"

        // 작업의 요청을 가로챈 후 특정 작업을 하기위한 인터셉터 선언
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        //클라이언트 생성
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // java 객체 <-> JSON
        var gson = GsonBuilder().setLenient().create()

        // retrofit 객체 초기화
        fun create(): RetrofitService {
            val retrofit by lazy {
                Retrofit.Builder()
                    .baseUrl(baseUrl) // 어떤 서버로 네트워크 통신할건지
                    .addConverterFactory(GsonConverterFactory.create(gson)) // 통신 완료 후, gson converter 이용해 데이터 파싱
                    .addConverterFactory(SimpleXmlConverterFactory.create()) // 통신 완료 후, xml converter 이용해 데이터 파싱
                    .client(client)
                    .build() // retrofit에 반환
            }
            return retrofit.create(RetrofitService::class.java) // RetrofitService의 구현 생성
        }


    }
}