package com.abdulmanov.laovay.mvp.presenters.word

import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.views.word.DetailsWordView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class DetailsWordPresenter(private val repository: Repository):MvpPresenter<DetailsWordView>(){

    private val compositeDisposable = CompositeDisposable()

    fun fetchWord(word: Word){
        viewState.showProgressBar(true)
        compositeDisposable.add(
            repository.fetchWord(word.id,word.word).subscribe {detailsWord->
                viewState.showProgressBar(false)
                viewState.showData(detailsWord)
            }
        )
    }

    fun saveWordInFavorites(word: Word){
        compositeDisposable.add(
            repository.saveWordInFavorites(word).subscribe(
                {
                    viewState.showMessage(R.string.message_add_word)
                },
                {
                    viewState.showMessage(R.string.message_exists_word)
                }
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}