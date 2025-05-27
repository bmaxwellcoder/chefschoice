package com.example.chefschoice.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteList") //tableName not required in Codelab, why?
data class FavoriteEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,//automatically creates a unique id for each recipe
//can add Int = 0;
    @ColumnInfo(name = "recipe_name") val title: String,
    @ColumnInfo(name = "image_url") val image: String,
    @ColumnInfo(name = "favorite") var favorite: Boolean = true // needs to be set to true for heart to fill red
)
