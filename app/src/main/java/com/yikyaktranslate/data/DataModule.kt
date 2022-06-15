package com.yikyaktranslate.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yikyaktranslate.data.framework.remote.TranslationService
import com.yikyaktranslate.data.source.TranslationRemoteDataSource
import com.yikyaktranslate.data.source.TranslationRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractDataModule {

    @Binds
    @Singleton
    abstract fun bindTranslationRemoteDataSource(
        translationRemoteDataSourceImpl: TranslationRemoteDataSourceImpl,
    ): TranslationRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiFactoryConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun provideTranslationService(
        moshiConverterFactory: MoshiConverterFactory
    ): TranslationService {
        return Retrofit.Builder()
            .baseUrl(TranslationService.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create(TranslationService::class.java)
    }
}
