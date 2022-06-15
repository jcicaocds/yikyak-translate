package com.yikyaktranslate.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yikyaktranslate.data.repository.TranslationRepository
import com.yikyaktranslate.presentation.model.TranslateViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TranslateViewModel @Inject constructor(
    private val translationRepository: TranslationRepository,
) : ViewModel() {

    var viewState by mutableStateOf(TranslateViewState())
        private set

    init {
        loadLanguages()
    }

    /**
     * Loads the languages from our service
     */
    private fun loadLanguages() {
        viewModelScope.launch {
            val availableLanguages = try {
                translationRepository.getLanguages()
            } catch (exception: Exception) {
                Log.e(javaClass.name, exception.toString())
                emptyList()
            }
            viewState = viewState.copy(languages = availableLanguages)
        }
    }

    /**
     * Translates the text provided to the selected language
     */
    fun translateText() {
        viewModelScope.launch {
            val translatedText = try {
                translationRepository
                    .translate(
                        text = viewState.inputText.text,
                        source = viewState.sourceLanguageCode,
                        target = viewState.targetLanguageCode,
                    )
                    .translatedText
            } catch (exception: Exception) {
                Log.e(javaClass.name, exception.toString())
                ""
            }
            viewState = viewState.copy(translatedText = translatedText)
        }
    }

    /**
     * Updates the data when there's new text from the user
     *
     * @param newText TextFieldValue that contains user input we want to keep track of
     */
    fun onInputTextChange(newText: TextFieldValue) {
        viewState = viewState.copy(inputText = newText)
    }

    /**
     * Updates the selected target language when the user selects a new language
     *
     * @param newLanguageIndex Represents the index for the chosen language in the list of languages
     */
    fun onTargetLanguageChange(newLanguageIndex: Int) {
        viewState = viewState.copy(targetLanguageIndex = newLanguageIndex)
    }

}