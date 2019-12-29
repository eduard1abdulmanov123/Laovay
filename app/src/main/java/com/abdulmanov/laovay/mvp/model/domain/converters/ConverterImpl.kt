package com.abdulmanov.laovay.mvp.model.domain.converters

import com.abdulmanov.laovay.mvp.model.core.favorites.entities.FavoriteWord
import com.abdulmanov.laovay.mvp.model.core.favorites.entities.ViewedWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ChWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ExampleWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.RuWord
import com.abdulmanov.laovay.mvp.model.domain.model.Example
import com.abdulmanov.laovay.mvp.model.domain.model.Word
import org.json.JSONObject

class ConverterImpl:Converter {

    companion object {
        const val COMMON_TEXT = 's'
        const val GRAY_TEXT = 'x'
        const val BOLD_TEXT = 'b'
        const val ITALIC_TEXT = 'i'
        const val RED_TEXT = 'c'
        const val COLOR_NORMAL = "#D52E3643"
        const val MAX_ITEMS = 7
    }

    override fun convertChWordToUi(chWord: ChWord, short: Boolean): Word {
        return with(convertJsonToString(chWord.translate, if (short) MAX_ITEMS else -1)) {
            Word(chWord.rowId, chWord.word, this, chWord.transcription)
        }
    }

    override fun convertRuWordToUi(ruWord: RuWord, short: Boolean): Word {
        return with(convertJsonToString(ruWord.translate, if (short) MAX_ITEMS else -1)) {
            Word(ruWord.rowId, ruWord.word, this, null)
        }
    }

    override fun convertExampleWordToUi(exampleWord: ExampleWord, substring: String): Example {
        return Example(exampleWord.word!!, exampleWord.translate!!, substring)
    }

    override fun convertFavoriteWordToUi(favoriteWord: FavoriteWord): Word {
        return Word(
            favoriteWord.id,
            favoriteWord.word,
            favoriteWord.translate,
            favoriteWord.transcription
        )
    }

    override fun convertViewedWordToUi(viewedWord: ViewedWord): Word {
        return Word(viewedWord.id, viewedWord.word, viewedWord.translate, viewedWord.transcription)
    }

    override fun convertUiToFavoriteWord(word: Word): FavoriteWord {
        return FavoriteWord(word.id, word.word, word.translate, word.transcription)
    }

    override fun convertUiToViewedWord(word: Word): ViewedWord {
        return ViewedWord(word.id, word.word, word.translate, word.transcription)
    }

    //Если max = -1, то спарсить полностью
    private fun convertJsonToString(json: String, max: Int): String {
        var count = 0
        var sb = ""
        val jsonObject = JSONObject(json)
        for (key in jsonObject.keys()) {
            val value = jsonObject.getString(key).replace("\n", "<br/>")
            sb += (when (key.last()) {
                COMMON_TEXT -> "<font COLOR=\'$COLOR_NORMAL\'><span>$value</span></font> "
                GRAY_TEXT -> "<font COLOR=\'GRAY\'><span>$value</span></font> "
                BOLD_TEXT -> "<font COLOR='$COLOR_NORMAL'><b>$value</b></font> "
                ITALIC_TEXT -> "<font COLOR='$COLOR_NORMAL'><i>$value</i></font> "
                RED_TEXT -> "<font COLOR=\'RED\'><span>$value</span></font> "
                else -> value
            })
            if (max != -1 && (++count) >= max) {
                break
            }
        }
        return sb
    }
}