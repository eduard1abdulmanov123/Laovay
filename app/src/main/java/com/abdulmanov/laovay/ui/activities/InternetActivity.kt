package com.abdulmanov.laovay.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.abdulmanov.laovay.R
import kotlinx.android.synthetic.main.activity_internet.*

class InternetActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_WORD = "EXTRA_WORD"
        private const val URL = "https://hanyu.baidu.com/zici/s?wd="
        fun newIntent(context: Context, word: String): Intent {
            return Intent(context, InternetActivity::class.java).apply {
                putExtra(EXTRA_WORD, word)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet)
        initUI()
    }

    override fun onBackPressed() {
        return if (activity_internet_web_view.canGoBack()) {
            activity_internet_web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        val word = intent!!.getStringExtra(EXTRA_WORD)!!

        with(activity_internet_toolbar) {
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener { finish() }
            title = word
        }

        with(activity_internet_web_view) {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webViewClient = CustomWebViewClient()
            loadUrl(URL + word)
        }
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            activity_internet_progress_bar.visibility = View.VISIBLE
            activity_internet_web_view.visibility = View.GONE
        } else {
            activity_internet_progress_bar.visibility = View.GONE
            activity_internet_web_view.visibility = View.VISIBLE
        }
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showProgressBar(true)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            showProgressBar(false)
        }
    }
}
