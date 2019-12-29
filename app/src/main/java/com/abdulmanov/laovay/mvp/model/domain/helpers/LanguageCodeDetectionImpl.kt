package com.abdulmanov.laovay.mvp.model.domain.helpers

import java.util.regex.Pattern

class LanguageCodeDetectionImpl: LanguageCodeDetection {

    override fun detectLanguage(text: String): LanguageCodeDetection.LanguageCode {
        return when{
            isChinesText(text) -> LanguageCodeDetection.LanguageCode.CH
            isRussianText(text) -> LanguageCodeDetection.LanguageCode.RU
            isEnglishText(text) -> LanguageCodeDetection.LanguageCode.EN
            else -> LanguageCodeDetection.LanguageCode.CH
        }
    }

    private fun isEnglishText(text:String):Boolean{
        return Pattern.compile("[a-zA-Z]*").matcher(text).find()
    }

    private fun isRussianText(text:String):Boolean{
        for (element in text) {
            val block = Character.UnicodeBlock.of(element)
            if (Character.UnicodeBlock.CYRILLIC == block||
                Character.UnicodeBlock.CYRILLIC_EXTENDED_A == block||
                Character.UnicodeBlock.CYRILLIC_EXTENDED_B == block||
                Character.UnicodeBlock.CYRILLIC_SUPPLEMENTARY == block) {
                return true
            }
        }
        return false
    }

    private fun isChinesText(text:String):Boolean{
        for (element in text) {
            val block = Character.UnicodeBlock.of(element)
            if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS == block ||
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == block ||
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == block ||
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C == block || // api 19, remove these if supporting lower versions
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D == block || // api 19
                Character.UnicodeBlock.CJK_COMPATIBILITY == block ||
                Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS == block ||
                Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS == block ||
                Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT == block ||
                Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT == block ||
                Character.UnicodeBlock.CJK_STROKES == block ||                        // api 19
                Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION == block ||
                Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS == block ||
                Character.UnicodeBlock.ENCLOSED_IDEOGRAPHIC_SUPPLEMENT == block ||    // api 19
                Character.UnicodeBlock.KANGXI_RADICALS == block ||
                Character.UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS == block) {
                return true
            }
        }
        return false
    }
}
