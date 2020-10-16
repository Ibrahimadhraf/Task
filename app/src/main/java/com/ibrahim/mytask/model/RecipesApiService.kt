package com.ibrahim.mytask.model

import com.ibrahim.mytask.utis.BASEURL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RecipesApiService {
    private val api= Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RecipesApi::class.java)
    fun  getRecipes(): Single<List<RecipesModel>> {
        return api.getDogs()
    }
}