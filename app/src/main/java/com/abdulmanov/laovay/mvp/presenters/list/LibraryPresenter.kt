package com.abdulmanov.laovay.mvp.presenters.list

import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.views.list.ListView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class LibraryPresenter(private val repository: Repository): MvpPresenter<ListView>() {

    private val compositeDisposable = CompositeDisposable()

    fun fetchFavoriteWords(){
        viewState.showProgressBar(true)
        compositeDisposable.add(
            repository.fetchFavoriteWords().subscribe{words->
                viewState.showProgressBar(false)
                if(words.isEmpty()){
                    viewState.showEmptyData(true)
                }else{
                    viewState.showEmptyData(false)
                    viewState.showData(words)
                }
            }
        )
    }

    fun removeFavoriteWord(word:Word){
        compositeDisposable.add(repository.removeWordInFavorites(word).subscribe{})
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
