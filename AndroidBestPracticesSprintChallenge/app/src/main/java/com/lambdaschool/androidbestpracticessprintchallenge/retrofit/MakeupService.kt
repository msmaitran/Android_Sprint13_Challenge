package com.lambdaschool.androidbestpracticessprintchallenge.retrofit

import com.lambdaschool.androidbestpracticessprintchallenge.model.Makeup
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeupService {
    @GET("products.json")
    fun getMakeupBrand(@Query("brand")brand: String): Single<List<Makeup>>
}