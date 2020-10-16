package com.ibrahim.mytask.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ibrahim.mytask.model.RecipesDatabase
import com.ibrahim.mytask.model.RecipesModel
import kotlinx.coroutines.launch

class RecipesDetailsViewModel(application: Application):BaseViewModel(application) {
    val recipesDetails =MutableLiveData<RecipesModel>()
    val recipesLoadErrors = MutableLiveData<Boolean>()
    fun fetch(uuid: Int) {
        launch {
            val recipeDetails =  RecipesDatabase(getApplication()).recipesDAO().getRecipeById(uuid)
            recipesDetails.value = recipeDetails

        }


    }
}