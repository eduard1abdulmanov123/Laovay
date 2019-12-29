package com.abdulmanov.laovay.mvp.model.domain

import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetection
import com.abdulmanov.laovay.mvp.model.domain.helpers.LanguageCodeDetectionImpl
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class LanguageCodeDetectionImplTest {

    private lateinit var detector: LanguageCodeDetection

    @Before
    fun setUp() {
        detector = LanguageCodeDetectionImpl()
    }

    @Test
    fun testIsEnglishText(){
        assertEquals(detector.detectLanguage("baron, eeadsa"),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertEquals(detector.detectLanguage("baron dadad"),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertEquals(detector.detectLanguage("  baron   "),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertEquals(detector.detectLanguage("baron  "),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertEquals(detector.detectLanguage("cadsaddad"),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertNotEquals(detector.detectLanguage("с"),
            LanguageCodeDetection.Companion.LanguageCode.EN)
        assertNotEquals(detector.detectLanguage("сdadwa"),
            LanguageCodeDetection.Companion.LanguageCode.EN)
    }

    @Test
    fun testIsRuText(){
        assertEquals(detector.detectLanguage("Привет вфыв"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
        assertEquals(detector.detectLanguage("царь во дворца,вф вф"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
        assertEquals(detector.detectLanguage("Сеня го по шавухи"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
        assertEquals(detector.detectLanguage("герой"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
        assertNotEquals(detector.detectLanguage("dadadad"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
        assertNotEquals(detector.detectLanguage("  dadadsa  dawdasda"),
            LanguageCodeDetection.Companion.LanguageCode.RU)
    }

    @Test
    fun testIsChinesText(){
        assertEquals(detector.detectLanguage("你好"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
        assertEquals(detector.detectLanguage("富康"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
        assertEquals(detector.detectLanguage("谷歌"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
        assertEquals(detector.detectLanguage("你好"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
        assertNotEquals(detector.detectLanguage("dadadad"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
        assertNotEquals(detector.detectLanguage("вфвфаф вфвфв фвц3123"),
            LanguageCodeDetection.Companion.LanguageCode.CH)
    }
}