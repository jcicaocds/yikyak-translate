package com.yikyaktranslate.data.source

import com.yikyaktranslate.data.model.Language
import com.yikyaktranslate.data.model.Translation
import com.yikyaktranslate.data.framework.remote.TranslationService
import javax.inject.Inject

class TranslationRemoteDataSourceImpl @Inject constructor(
    private val translationService: TranslationService,
) : TranslationRemoteDataSource {
    override suspend fun getLanguages(): List<Language> {
        return translationService.getLanguages()
    }

    override suspend fun translate(
        text: String,
        source: String,
        target: String,
    ): Translation {
        return translationService.translate(
            text = text,
            source = source,
            target = target,
        )
    }
}
