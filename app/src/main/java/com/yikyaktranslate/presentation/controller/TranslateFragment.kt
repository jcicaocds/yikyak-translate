package com.yikyaktranslate.presentation.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yikyaktranslate.presentation.theme.YikYakTranslateTheme
import com.yikyaktranslate.presentation.view.TranslateView
import com.yikyaktranslate.presentation.viewmodel.TranslateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslateFragment : Fragment() {

    private val translateViewModel: TranslateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                YikYakTranslateTheme {
                    with(translateViewModel.viewState) {
                        TranslateView(
                            inputText = inputText,
                            onInputChange = translateViewModel::onInputTextChange,
                            languages = languagesToDisplay,
                            targetLanguageIndex = targetLanguageIndex,
                            onTargetLanguageSelected = translateViewModel::onTargetLanguageChange,
                            onTranslateClick = translateViewModel::translateText,
                            translatedText = translatedText,
                        )
                    }
                }
            }
        }
    }
}