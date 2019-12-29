package com.abdulmanov.laovay.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulmanov.laovay.app.BaseApp

import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.common.visibility
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.abdulmanov.laovay.ui.activities.DetailsWordActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_library.*
import javax.inject.Inject
import com.abdulmanov.laovay.mvp.presenters.list.LibraryPresenter
import com.abdulmanov.laovay.mvp.views.list.ListView
import com.abdulmanov.laovay.ui.adapters.LibraryAdapter

class LibraryFragment : MvpAppCompatFragment(),ListView {

    @Inject
    @InjectPresenter
    lateinit var presenter: LibraryPresenter

    @ProvidePresenter
    fun providePresenter(): LibraryPresenter {
        return presenter
    }

    private val adapter = LibraryAdapter({ startActivity(DetailsWordActivity.newIntent(context!!, it)) }) {
        presenter.removeFavoriteWord(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApp.instance.plusFragmentComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.fetchFavoriteWords()
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.instance.clearFragmentComponent()
    }

    override fun showProgressBar(show: Boolean) {
        library_recycler_view.visibility(!show)
        library_progress_bar.visibility(show)
    }

    override fun showEmptyData(show: Boolean) {
        library_recycler_view.visibility(!show)
        library_empty_data.visibility(show)
    }

    override fun showData(words: List<Word>) {
        adapter.swapData(words)
    }

    private fun initUI(){
        library_title.text = getString(R.string.favorite)

        with(library_recycler_view){
            layoutManager = LinearLayoutManager(context)
            adapter = this@LibraryFragment.adapter
        }
    }
}
