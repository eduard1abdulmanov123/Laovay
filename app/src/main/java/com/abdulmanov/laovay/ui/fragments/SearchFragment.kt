package com.abdulmanov.laovay.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdulmanov.laovay.app.BaseApp

import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.common.visibility
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.abdulmanov.laovay.mvp.presenters.list.SearchPresenter
import com.abdulmanov.laovay.ui.adapters.WordsAdapter
import com.abdulmanov.laovay.ui.activities.DetailsWordActivity
import com.abdulmanov.laovay.mvp.views.list.ListView
import com.abdulmanov.searchview.focus
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment : MvpAppCompatFragment(), ListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        return presenter
    }

    private val adapter = WordsAdapter({
        startActivity(DetailsWordActivity.newIntent(context!!, it))
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApp.instance.plusFragmentComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.fetchViewedWords()
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.instance.plusFragmentComponent()
    }

    override fun showProgressBar(show: Boolean) {
        fragment_search_progress_bar.visibility(show)
        fragment_search_recycler_view.visibility(!show)
    }

    override fun showEmptyData(show: Boolean) {
        fragment_search_empty_data.visibility(show)
        fragment_search_recycler_view.visibility(!show)
    }

    override fun showData(words: List<Word>) {
       adapter.swapData(words)
    }

    private fun initUI(){
        initRecyclerView()
        initSearchView()
    }

    private fun initRecyclerView(){
        with(fragment_search_recycler_view){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        fragment_search_search_view.editText.focus(false)
                    }
                }
            })
            adapter = this@SearchFragment.adapter
        }
    }

    private fun initSearchView(){
        with(fragment_search_search_view){
            addTextChangedListener({
                if(it!=null&&it.isEmpty()){
                    presenter.fetchViewedWords()
                }else{
                    presenter.findWords(it.toString())
                }
            })

            setOnEditorActionListener {
                presenter.findWords(it)
            }
        }
    }
}
