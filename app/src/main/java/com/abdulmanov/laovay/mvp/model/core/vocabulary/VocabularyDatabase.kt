package com.abdulmanov.laovay.mvp.model.core.vocabulary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abdulmanov.laovay.mvp.model.core.common.Constants.VocabularyDatabase.Companion.VOCABULARY_DATABASE_NAME

import com.abdulmanov.laovay.mvp.model.core.common.Constants.VocabularyDatabase.Companion.VOCABULARY_DATABASE_VERSION
import com.abdulmanov.laovay.mvp.model.core.vocabulary.dao.VocabularyDao
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.RuWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ChWord
import com.abdulmanov.laovay.mvp.model.core.vocabulary.entities.ExampleWord

@Database(
    entities = [RuWord::class,ChWord::class,ExampleWord::class],
    version = VOCABULARY_DATABASE_VERSION,
    exportSchema = false)
abstract class VocabularyDatabase: RoomDatabase() {

    abstract fun getVocabularyDao(): VocabularyDao

    companion object {

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {}
        }

        fun getDatabase(context: Context): VocabularyDatabase {
            return Room.databaseBuilder(
                context,
                VocabularyDatabase::class.java,
                VOCABULARY_DATABASE_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}