package com.abdulmanov.laovay.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdulmanov.laovay.R
import com.abdulmanov.laovay.mvp.model.core.common.Constants.VocabularyDatabase.Companion.VOCABULARY_DATABASE_NAME
import com.abdulmanov.laovay.mvp.model.core.common.copyAttachedDatabase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SplashScreenActivity : AppCompatActivity() {

    private var copyDatabaseDisposable:Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        prepareApplication()
    }

    override fun onStop() {
        super.onStop()
        copyDatabaseDisposable?.dispose()
    }

    private fun prepareApplication(){
        copyDatabaseDisposable = Completable.create {
            copyAttachedDatabase(VOCABULARY_DATABASE_NAME, VOCABULARY_DATABASE_NAME) {
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(0, 0)
            }
    }
}
