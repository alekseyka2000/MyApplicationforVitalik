package com.startandroid.myapplicationforvitalik

import android.telecom.CallScreeningService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "78f892e243cb50e4f8b28c916bdc1592",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}