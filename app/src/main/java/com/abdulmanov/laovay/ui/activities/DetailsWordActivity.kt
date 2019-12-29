package com.abdulmanov.laovay.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdulmanov.laovay.app.BaseApp
import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.common.visibility
import com.abdulmanov.laovay.dagger.module.ActivityModule
import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetection
import com.abdulmanov.laovay.mvp.model.domain.model.DetailsWord
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import com.abdulmanov.laovay.mvp.presenters.word.DetailsWordPresenter
import com.abdulmanov.laovay.ui.adapters.ExamplesAdapter
import com.abdulmanov.laovay.mvp.views.word.DetailsWordView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_details_word.*
import java.util.*
import javax.inject.Inject

class DetailsWordActivity : MvpAppCompatActivity(),
    DetailsWordView {

    companion object {
        private const val EXTRA_WORD = "WORD"
        fun newIntent(context: Context, word: Word): Intent {
            return Intent(context, DetailsWordActivity::class.java).apply {
                putExtra(EXTRA_WORD, word)
            }
        }
    }

    @Inject
    lateinit var languageCodeDetection: LanguageCodeDetection

    @Inject
    @InjectPresenter
    lateinit var presenter: DetailsWordPresenter

    @ProvidePresenter
    fun providePresenter(): DetailsWordPresenter {
        return presenter
    }

    private lateinit var textToSpeech: TextToSpeech
    private val adapter = ExamplesAdapter()
    private val word by lazy {
        intent!!.getParcelableExtra<Word>(EXTRA_WORD)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApp.instance.plusActivityComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_word)
        initUI()
        presenter.fetchWord(word)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApp.instance.clearActivityComponent()
        if (details_word_voice.visibility != View.GONE) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    override fun showData(detailsWord: DetailsWord) {
        details_word_translate.setText(
            HtmlCompat.fromHtml(
                detailsWord.word.translate,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            ), TextView.BufferType.SPANNABLE
        )

        if (detailsWord.examples.isNotEmpty()) {
            details_word_examples_recycler_view.visibility(true)
            adapter.swapData(detailsWord.examples)
        } else {
            details_word_examples_recycler_view.visibility(false)
            details_word_empty_examples.visibility(true)
        }
    }

    override fun showProgressBar(show: Boolean) {
        details_word_content.visibility(!show)
        details_word_progress_bar.visibility(show)
    }

    override fun showMessage(message: Int) {
        Snackbar.make(details_word_content, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun initUI() {
        details_word_word.text = word.word
        details_word_transcription.text = word.transcription ?: ""

        initToolbar()
        initRecyclerView()
        initTabLayout()

        if (languageCodeDetection.detectLanguage(word.word) != LanguageCodeDetection.LanguageCode.RU) {
            initTextToSpeech()

            with(details_word_internet) {
                visibility = View.VISIBLE
                setOnClickListener {
                    startActivity(InternetActivity.newIntent(context, word.word))
                }
            }
        }
    }

    private fun initToolbar() {
        with(details_word_toolbar) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
            inflateMenu(R.menu.menu_details_word)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_details_word_add -> presenter.saveWordInFavorites(word)
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun initRecyclerView() {
        with(details_word_examples_recycler_view) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@DetailsWordActivity.adapter
        }
    }

    private fun initTabLayout() {
        val createTabItem: (title: Int) -> View = {
            (layoutInflater.inflate(R.layout.tab_item_view, null) as TextView).apply {
                text = getString(it)
            }
        }

        with(details_word_tab_layout) {
            getTabAt(0)?.customView = createTabItem.invoke(R.string.tab_translate)
            getTabAt(1)?.customView = createTabItem.invoke(R.string.tab_example)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        details_word_examples.visibility(tab.position != 0)
                        details_word_translate_nested_scroll_view.visibility(tab.position == 0)
                    }
                }
            })
        }
    }

    private fun initTextToSpeech() {
        val handleTtsError: () -> Unit = {
            details_word_voice.visibility = View.GONE
            Snackbar.make(details_word_voice, R.string.tts_error, Snackbar.LENGTH_SHORT).show()
        }

        textToSpeech = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                when {
                    textToSpeech.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_AVAILABLE -> {
                        textToSpeech.language = Locale.CHINESE
                        textToSpeech.setPitch(0.7f)
                        textToSpeech.setSpeechRate(0.7f)
                    }
                    else -> handleTtsError.invoke()
                }
            } else {
                handleTtsError.invoke()
            }
        }

        with(details_word_voice) {
            visibility = View.VISIBLE
            setOnClickListener {
                val utteranceId = this.hashCode().toString()
                val text = details_word_word.text.toString()
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            }
        }
    }
}
