package com.abdulmanov.laovay.mvp.model.core.common

interface Constants {
    interface VocabularyDatabase {
        companion object {
            const val VOCABULARY_DATABASE_VERSION = 2
            const val VOCABULARY_DATABASE_NAME = "brks_01012.db"
        }
    }

    interface UserDatabase {
        companion object {
            const val USER_DATABASE_VERSION = 1
            const val USER_DATABASE_NAME = "user.db"
        }
    }
}