package com.ibrahim.mytask.viewModel
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.ibrahim.mytask.model.RecipesApiService
import com.ibrahim.mytask.model.RecipesDatabase
import com.ibrahim.mytask.model.RecipesModel
import com.ibrahim.mytask.utis.checkInternetConnection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class RecipesListViewModel(application: Application):BaseViewModel(application){
    private val  recipesApiService  = RecipesApiService();
      val recipes = MutableLiveData<List<RecipesModel>>()
    val recipesLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()



    fun invokeUi(context: Context){

     if( checkInternetConnection(context)){
       fetchRemote()
     }else{
        fetchFromDataBase()
     }


    }
    private fun fetchRemote() {
        isLoading.value = true
        disposable.add(
            recipesApiService
                .getRecipes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<List<RecipesModel>>() {

                        override fun onSuccess(recipesList: List<RecipesModel>) {
                            //Log.d("Respose" , " aftergetis${recipes}")
                            recipesRetrieved(recipesList)

                            storeDogsLocally(recipesList)

                        }


                        override fun onError(e: Throwable) {
                            recipesLoadError.value = true
                            isLoading.value = false

                            e.printStackTrace()
                        }

                    }
                )

        )

    }
    private fun storeDogsLocally(list: List<RecipesModel>) {
        launch {
            val dao = RecipesDatabase(getApplication()).recipesDAO()
            dao.deleteAllRecipes()

            val result = dao.insertAll(*list.toTypedArray())
            var  i=0
            while (i<list.size){
                list[i].uuId=result[i].toInt()
                i++
            }

            recipesRetrieved(list)
        }

    }
    private fun recipesRetrieved(dogsList: List<RecipesModel>) {
        recipes.value = dogsList
        recipesLoadError.value = false
        isLoading.value = false

    }
    private fun fetchFromDataBase() {
        isLoading.value=true
        launch {
            val recipes=RecipesDatabase(getApplication()).recipesDAO().getAllRecipes()
            recipesRetrieved(recipes)

        }
    }
    fun fetchByName(mame: String) {
        launch {
            val recipeByName = RecipesDatabase(getApplication()).recipesDAO().getRecipesByName(mame)
            recipes.value=recipeByName

        }


    }
    fun fetchByCalories() {
        launch {
            val recipeByName = RecipesDatabase(getApplication()).recipesDAO().getAllRecipesSortedByCalories()
            recipes.value=recipeByName

        }


    }
    fun fetchByFat() {
        launch {
            val recipeByName = RecipesDatabase(getApplication()).recipesDAO().getAllRecipesSortedByFat()
            recipes.value=recipeByName

        }


    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}