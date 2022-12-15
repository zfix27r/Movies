package ru.zfix27r.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.movies.data.TopRepositoryImpl
import ru.zfix27r.movies.domain.usecase.GetFilmUseCase
import ru.zfix27r.movies.domain.usecase.GetTopUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetTopUseCase(repository: TopRepositoryImpl): GetTopUseCase {
        return GetTopUseCase(repository)
    }

    @Provides
    fun provideGetFilmUseCase(repository: TopRepositoryImpl): GetFilmUseCase {
        return GetFilmUseCase(repository)
    }
}