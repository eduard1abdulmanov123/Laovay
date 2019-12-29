package com.abdulmanov.laovay.mvp.model.core.favorites.dao

import androidx.room.*
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.FavoriteWord
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.ViewedWord
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM favorite_words")
    fun fetchFavoriteWords(): Flowable<List<FavoriteWord>>

    @Insert
    fun insertFavoriteWord(favoriteWord: FavoriteWord):Completable

    @Query("DELETE FROM favorite_words WHERE id = :id")
    fun deleteFavoriteWord(id:Int):Completable

    @Query("SELECT * FROM viewed_words")
    fun fetchViewedWords():Single<List<ViewedWord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertViewedWord(viewedWord: ViewedWord)

}