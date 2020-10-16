package com.ibrahim.mytask.model

import com.ibrahim.mytask.utis.recipesEndPoint
import io.reactivex.Single
import retrofit2.http.GET

interface RecipesApi {
    @GET(recipesEndPoint)
    fun getDogs () : Single<List<RecipesModel>>
}