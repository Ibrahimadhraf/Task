package com.ibrahim.mytask.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface RecipesDAO {
    @Insert
    suspend fun insertAll( vararg dogs: RecipesModel):List<Long>
    @Query("SELECT * FROM recipesmodel")
    suspend fun getAllRecipes(): List<RecipesModel>
    @Query("SELECT * FROM recipesmodel WHERE uuid = :recipeId")
    suspend fun getRecipeById(recipeId: Int): RecipesModel
    @Query("SELECT * FROM recipesmodel WHERE name = :recipeName")
    suspend fun getRecipesByName(recipeName: String): List<RecipesModel>
    @Query("SELECT *FROM recipesmodel ORDER BY  calories ")
     suspend fun getAllRecipesSortedByCalories():List<RecipesModel>
    @Query("SELECT *FROM recipesmodel ORDER BY  fats ")
    suspend fun getAllRecipesSortedByFat():List<RecipesModel>
    @Query("DELETE FROM recipesmodel")
    suspend fun deleteAllRecipes()

}