package com.abdulmanov.laovay.mvp.views.word

import com.abdulmanov.laovay.mvp.model.domain.model.DetailsWord
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface DetailsWordView:MvpView{

    fun showData(detailsWord:DetailsWord)

    fun showProgressBar(show:Boolean)

    fun showMessage(message:Int)
}