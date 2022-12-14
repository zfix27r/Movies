package ru.zfix27r.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.movies.data.TopRepositoryImpl
import ru.zfix27r.movies.domain.usecase.GetFilmUseCase
import ru.zfix27r.movies.domain.usecase.GetTopPagingDataUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetTopPagingSourceUseCase(repository: TopRepositoryImpl): GetTopPagingDataUseCase {
        return GetTopPagingDataUseCase(repository)
    }

    @Provides
    fun provideGetFilmUseCase(repository: TopRepositoryImpl): GetFilmUseCase {
        return GetFilmUseCase(repository)
    }
}