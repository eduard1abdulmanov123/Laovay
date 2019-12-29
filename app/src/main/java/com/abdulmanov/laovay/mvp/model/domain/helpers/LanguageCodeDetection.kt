package com.abdulmanov.laovay.mvp.model.domain.helpers

interface LanguageCodeDetection {

    sealed class LanguageCode{
        object RU : LanguageCode()
        object EN : LanguageCode()
        object CH : LanguageCode()
    }

    fun detectLanguage(text:String): LanguageCode
}