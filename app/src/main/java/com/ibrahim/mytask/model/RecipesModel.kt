package com.ibrahim.mytask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipesModel(
    @ColumnInfo(name = "calories")
    val calories: String,
    @ColumnInfo(name = "carbos")
    val carbos: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "difficulty")
    val difficulty: Int,
    @ColumnInfo(name = "fats")
    val fats: String,
    @ColumnInfo(name = "headline")
    val headline: String,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "proteins")
    val proteins: String,
    @ColumnInfo(name = "thumb")
    val thumb: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "country")
    val country: String? = null

){
    @PrimaryKey(autoGenerate = true)
    var uuId:Int=0
}
data class  RecipePalette(var color: Int)