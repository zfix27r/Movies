package ru.zfix27r.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.movies.data.FilmTopRepositoryImpl
import ru.zfix27r.movies.domain.usecase.GetTopFilmsUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetTopFilmsUseCase(repository: FilmTopRepositoryImpl): GetTopFilmsUseCase {
        return GetTopFilmsUseCase(repository)
    }
}