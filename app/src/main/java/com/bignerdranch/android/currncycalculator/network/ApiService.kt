package com.bignerdranch.android.currncycalculator.network

  import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.getgeoapi.com/v2/currency/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("convert?api_key=df80c136dc0191112bdeb99b5ade88be84ad481a")
    suspend fun convertCurrency(
        @Query("api_key") access_key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): ApiResponse
}

object CurrencyApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}

//    val asd = "http://api.currencylayer.com/live?access_key=90999e41a30fb6472e23258c4a595fa7"
//    "https://api.currencylayer.com/convert?access_key=90999e41a30fb6472e23258c4a595fa7&from=EUR&to=GBP"
//https://api.getgeoapi.com/v2/currency/convert?api_key=df80c136dc0191112bdeb99b5ade88be84ad481a
