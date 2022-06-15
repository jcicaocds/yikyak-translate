package com.yikyaktranslate.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yikyaktranslate.R
import com.yikyaktranslate.model.Language
import com.yikyaktranslate.service.face.TranslationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TranslateViewModel(application: Application) : AndroidViewModel(application) {

    private val translationService = TranslationService.create()

    // Code for the source language that we are translating from; currently hardcoded to English
    private val sourceLanguageCode: String = application.getString(R.string.source_language_code)

    // List of Languages that we get from the back end
    private val languages: MutableStateFlow<List<Language>> by lazy {
        MutableStateFlow<List<Language>>(listOf()).also {
            loadLanguages()
        }
    }

    // List of names of languages to display to user
    val languagesToDisplay = languages.map { it.map { language -> language.name } }.asLiveData()

    // Index within languages/languagesToDisplay that the user has selected
    val targetLanguageIndex = mutableStateOf(0)

    // Text that the user has input to be translated
    private val _textToTranslate = MutableStateFlow(TextFieldValue(""))
    val textToTranslate = _textToTranslate.asLiveData()

    // Translated text
    private val _translatedText = MutableStateFlow("")
    val translatedText = _translatedText.asLiveData()

    /**
     * Loads the languages from our service
     */
    private fun loadLanguages() {
        viewModelScope.launch {
            val availableLanguages = try {
                translationService.getLanguages()
            } catch (exception: Exception) {
                Log.e(javaClass.name, exception.toString())
                emptyList()
            }
            languages.value = availableLanguages
        }
    }

    /**
     * Translates the text provided to the selected language
     */
    fun translateText() {
        val selectedLanguageIndex = targetLanguageIndex.value
        val targetLanguageCode = languages.value[selectedLanguageIndex].code

        viewModelScope.launch {
            val translatedText = try {
                translationService
                    .translate(
                        text = textToTranslate.value?.text.orEmpty(),
                        source = sourceLanguageCode,
                        target = targetLanguageCode,
                    )
                    .translatedText
            } catch (exception: Exception) {
                Log.e(javaClass.name, exception.toString())
                ""
            }
            _translatedText.value = translatedText
        }
    }

    /**
     * Updates the data when there's new text from the user
     *
     * @param newText TextFieldValue that contains user input we want to keep track of
     */
    fun onInputTextChange(newText: TextFieldValue) {
        _textToTranslate.value = newText
    }

    /**
     * Updates the selected target language when the user selects a new language
     *
     * @param newLanguageIndex Represents the index for the chosen language in the list of languages
     */
    fun onTargetLanguageChange(newLanguageIndex: Int) {
        targetLanguageIndex.value = newLanguageIndex
    }

}