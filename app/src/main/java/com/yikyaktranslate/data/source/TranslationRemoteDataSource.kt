package com.yikyaktranslate.data.source

import com.yikyaktranslate.data.model.Language
import com.yikyaktranslate.data.model.Translation

interface TranslationRemoteDataSource {
    suspend fun getLanguages(): List<Language>
    suspend fun translate(
        text: String,
        source: String,
        target: String,
    ): Translation
}
