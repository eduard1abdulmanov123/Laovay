package com.abdulmanov.laovay.mvp.model.domain.converters

import com.abdulmanov.laovay.mvp.model.core.favorites.entities.FavoriteWord
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.ViewedWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ChWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ExampleWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.RuWord
import com.abdulmanov.laovay.mvp.model.domain.model.Example
import com.abdulmanov.laovay.mvp.model.domain.model.Word

interface Converter {

    fun convertChWordToUi(chWord:ChWord, short:Boolean = false):Word

    fun convertRuWordToUi(ruWord:RuWord, short: Boolean = false):Word

    fun convertExampleWordToUi(exampleWord: ExampleWord,substring:String):Example

    fun convertFavoriteWordToUi(favoriteWord: FavoriteWord):Word

    fun convertViewedWordToUi(viewedWord: ViewedWord):Word

    fun convertUiToFavoriteWord(word: Word):FavoriteWord

    fun convertUiToViewedWord(word: Word):ViewedWord

}