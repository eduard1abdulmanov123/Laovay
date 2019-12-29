package com.abdulmanov.laovay.mvp.model.domain.repository

import com.abdulmanov.laovay.mvp.model.core.favorites.dao.UserDao
import com.abdulmanov.laovay.mvp.model.core.vocabulary.dao.VocabularyDao
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ExampleWord
import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetection
import com.abdulmanov.laovay.mvp.model.domain.converters.Converter
import com.abdulmanov.laovay.mvp.model.domain.model.DetailsWord
import com.abdulmanov.laovay.mvp.model.domain.model.Example
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(
    private val vocabularyDb: VocabularyDao,
    private val userDb:UserDao,
    private val languageCodeDetection: LanguageCodeDetection,
    private val converter:Converter
):Repository {

    private val saveWordInViewedWord: (detailsWord: DetailsWord) -> Unit = {
        userDb.insertViewedWord(converter.convertUiToViewedWord(it.word))
    }

    private val findExample: (examples: Single<List<ExampleWord>>, query: String) -> Single<List<Example>> = { examples, query ->
        examples.map {it.map { example -> converter.convertExampleWordToUi(example, query) } }
    }

    override fun findWords(query: String): Single<List<Word>> {
        return when (languageCodeDetection.detectLanguage(query)) {
            LanguageCodeDetection.LanguageCode.CH -> vocabularyDb.findChWords(query).map {
                it.map { chWord -> converter.convertChWordToUi(chWord, true) }
            }
            LanguageCodeDetection.LanguageCode.RU -> vocabularyDb.findRuWords(query).map {
                it.map { ruWord -> converter.convertRuWordToUi(ruWord, true) }
            }
            LanguageCodeDetection.LanguageCode.EN -> vocabularyDb.findChWordsByTranscription(query).map {
                it.map { chWord -> converter.convertChWordToUi(chWord, true) }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchWord(id: Int, word: String): Single<DetailsWord> {
        return when (languageCodeDetection.detectLanguage(word)) {
            LanguageCodeDetection.LanguageCode.CH -> getDetailsChWord(id, word)
            LanguageCodeDetection.LanguageCode.RU -> getDetailsRuWord(id, word)
            LanguageCodeDetection.LanguageCode.EN -> getDetailsChWord(id, word)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchFavoriteWords(): Flowable<List<Word>> {
        return userDb.fetchFavoriteWords()
            .map { it.map { favoriteWord -> converter.convertFavoriteWordToUi(favoriteWord) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveWordInFavorites(word: Word): Completable {
        return userDb.insertFavoriteWord(converter.convertUiToFavoriteWord(word))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchViewedWords(): Single<List<Word>> {
        return userDb.fetchViewedWords()
            .map { it.map { viewedWord -> converter.convertViewedWordToUi(viewedWord) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeWordInFavorites(word: Word): Completable {
        return userDb.deleteFavoriteWord(word.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getDetailsChWord(id: Int, query: String): Single<DetailsWord> {
        return Single.zip(
            vocabularyDb.fetchChWord(id).map { converter.convertChWordToUi(it) },
            findExample(vocabularyDb.findExamplesForChWord(query), query),
            BiFunction<Word, List<Example>, DetailsWord> { words, examples ->
                DetailsWord(words, examples)
            }
        ).doOnSuccess(saveWordInViewedWord)
    }

    private fun getDetailsRuWord(id: Int, query: String): Single<DetailsWord> {
        return Single.zip(
            vocabularyDb.fetchRuWord(id).map { converter.convertRuWordToUi(it) },
            findExample(vocabularyDb.findExamplesForRuWord(query), query),
            BiFunction<Word, List<Example>, DetailsWord> { words, examples ->
                DetailsWord(words, examples)
            }
        ).doOnSuccess(saveWordInViewedWord)
    }
}
