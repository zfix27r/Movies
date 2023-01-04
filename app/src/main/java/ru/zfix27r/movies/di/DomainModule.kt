package ru.zfix27r.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.movies.data.MainRepositoryImpl
import ru.zfix27r.movies.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetMainMinUseCase(repository: MainRepositoryImpl) = GetMainMinUseCase(repository)

    @Provides
    fun provideGetTopPagingSourceUseCase(repository: MainRepositoryImpl) =
        GetTopPagingDataUseCase(repository)

    @Provides
    fun provideGetFilmUseCase(repository: MainRepositoryImpl) = GetFilmUseCase(repository)
}