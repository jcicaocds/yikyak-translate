package com.yikyaktranslate.presentation.model

import androidx.compose.ui.text.input.TextFieldValue
import com.yikyaktranslate.data.model.Language

data class TranslateViewState(
    val languages: List<Language> = emptyList(),
    val targetLanguageIndex: Int = 0,
    val inputText: TextFieldValue = TextFieldValue(),
    val translatedText: String = "",
) {
    val languagesToDisplay: List<String> by lazy {
        languages.map(Language::name)
    }

    val targetLanguageCode: String
        get() = languages[targetLanguageIndex].code

    // TODO: Get rid of this hardcoded value as soon as possible
    val sourceLanguageCode: String = "en"
}
