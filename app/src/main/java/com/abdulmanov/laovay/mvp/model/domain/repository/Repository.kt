package com.abdulmanov.laovay.mvp.model.domain.repository

import com.abdulmanov.laovay.mvp.model.domain.model.DetailsWord
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface Repository {

    fun findWords(query:String): Single<List<Word>>

    fun fetchWord(id:Int,word:String):Single<DetailsWord>

    fun fetchFavoriteWords(): Flowable<List<Word>>

    fun fetchViewedWords():Single<List<Word>>

    fun saveWordInFavorites(word: Word):Completable

    fun removeWordInFavorites(word:Word):Completable
}