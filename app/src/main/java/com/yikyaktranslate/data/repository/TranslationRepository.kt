package com.yikyaktranslate.data.repository

import com.yikyaktranslate.data.source.TranslationRemoteDataSource
import com.yikyaktranslate.data.model.Language
import com.yikyaktranslate.data.model.Translation
import javax.inject.Inject

class TranslationRepository @Inject constructor(
    private val translationRemoteDataSource: TranslationRemoteDataSource,
) {
    suspend fun getLanguages(): List<Language> {
        return translationRemoteDataSource.getLanguages()
    }

    suspend fun translate(text: String, source: String, target: String): Translation {
        return translationRemoteDataSource.translate(
            text = text,
            source = source,
            target = target,
        )
    }
}
