package com.abdulmanov.laovay.mvp.model.core.vocabulary.dao

import androidx.room.Dao
import androidx.room.Query
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.RuWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ChWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ExampleWord
import io.reactivex.Single

@Dao
interface VocabularyDao {
    @Query( "SELECT *,rowid  FROM fts_word WHERE word MATCH '*' || :query || '*' ORDER BY length(word) LIMIT 100")
    fun findChWords(query:String): Single<List<ChWord>>

    @Query("SELECT *,rowid FROM fts_word_rus WHERE word MATCH '' || :query || '*' ORDER BY length(word) LIMIT 100")
    fun findRuWords(query: String):Single<List<RuWord>>

    @Query("SELECT *,rowid FROM fts_word WHERE transcriptForFind MATCH :query ORDER BY length(transcription) LIMIT 100")
    fun findChWordsByTranscription(query: String):Single<List<ChWord>>

    @Query("SELECT *,rowid FROM fts_word WHERE rowid = :rowId")
    fun fetchChWord(rowId:Int):Single<ChWord>

    @Query("SELECT *,rowid FROM fts_word_rus WHERE rowid = :rowId")
    fun fetchRuWord(rowId: Int):Single<RuWord>

    @Query("SELECT * FROM Example WHERE translate LIKE '%' || :query || '%' ORDER BY length(translate)")
    fun findExamplesForRuWord(query: String):Single<List<ExampleWord>>

    @Query("SELECT * FROM Example WHERE word like '%' || :query || '%' ORDER BY length(word)")
    fun findExamplesForChWord(query: String):Single<List<ExampleWord>>
}