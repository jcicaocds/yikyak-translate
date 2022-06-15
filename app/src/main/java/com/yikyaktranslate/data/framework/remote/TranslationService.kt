package com.yikyaktranslate.data.framework.remote

import com.yikyaktranslate.data.model.Language
import com.yikyaktranslate.data.model.Translation
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationService {

    @GET("/languages")
    suspend fun getLanguages(): List<Language>

    @POST("/translate")
    suspend fun translate(
        @Query("q") text: String,
        @Query("source") source: String,
        @Query("target") target: String,
    ): Translation

    companion object {
        // this official mirror site doesn't require api key
        const val BASE_URL = "https://libretranslate.de/"
    }
}
