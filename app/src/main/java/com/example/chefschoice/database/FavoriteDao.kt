package com.example.chefschoice.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteList ORDER BY id ASC")
    fun getAll(): Flow<List<FavoriteEntity>>

   /* @Query("SELECT * from item WHERE id = :id")
    fun getFavorite(id: Int): Flow<FavoriteEntity> */

    @Query("SELECT EXISTS(SELECT * FROM favoriteList WHERE id = :id)")
    fun recipeExists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    fun delete(favoriteEntity: FavoriteEntity)

}