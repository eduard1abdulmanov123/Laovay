package com.abdulmanov.laovay.mvp.presenters.list

import com.abdulmanov.laovay.mvp.model.domain.repository.Repository
import com.abdulmanov.laovay.mvp.views.list.ListView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SearchPresenter(
    private val repository:Repository
):MvpPresenter<ListView>() {

    private val compositeDisposable = CompositeDisposable()

    fun findWords(query:String){
        viewState.showEmptyData(false)
        viewState.showProgressBar(true)
        compositeDisposable.add(
            repository.findWords(query).subscribe(
                {
                    viewState?.showProgressBar(false)
                    if(it.isEmpty()){
                        viewState.showEmptyData(true)
                    }else{
                        viewState.showData(it)
                    }
                },
                {

                }
            )
        )
    }

    fun fetchViewedWords(){
        viewState.showProgressBar(true)
        compositeDisposable.add(
            repository.fetchViewedWords().subscribe{words->
                viewState.showProgressBar(false)
                viewState.showEmptyData(false)
                viewState.showData(words)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}