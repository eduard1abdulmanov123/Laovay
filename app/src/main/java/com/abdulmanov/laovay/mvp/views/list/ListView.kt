package com.abdulmanov.laovay.mvp.views.list

import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface ListView:MvpView {

    fun showProgressBar(show:Boolean)

    fun showEmptyData(show:Boolean)

    fun showData(words:List<Word>)
}
