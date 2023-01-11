package ru.zfix27r.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.movies.data.FilmRepositoryImpl
import ru.zfix27r.movies.data.MainRepositoryImpl
import ru.zfix27r.movies.data.SearchRepositoryImpl
import ru.zfix27r.movies.data.TopRepositoryImpl
import ru.zfix27r.movies.data.local.dao.FilmDao
import ru.zfix27r.movies.data.local.dao.TopBestDao
import ru.zfix27r.movies.data.local.dao.TopDao
import ru.zfix27r.movies.data.remote.KinopoiskApi
import ru.zfix27r.movies.domain.TopRepository
import ru.zfix27r.movies.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetMainMinUseCase(repository: MainRepositoryImpl) = GetMainMinUseCase(repository)

    @Provides
    fun provideGetSearchResultUseCase(repository: SearchRepositoryImpl) =
        GetSearchResultUseCase(repository)

    @Provides
    fun provideGetTopAwaitUseCase(repository: TopRepositoryImpl) = GetTopAwaitUseCase(repository)

    @Provides
    fun provideGetTopPopularUseCase(repository: TopRepositoryImpl) =
        GetTopPopularUseCase(repository)

    @Provides
    fun provideGetTopBestUseCase(repository: TopRepositoryImpl) = GetTopBestUseCase(repository)

    @Provides
    fun provideGetFilmUseCase(repository: FilmRepositoryImpl) = GetFilmUseCase(repository)

    @Provides
    fun provideTopRepository(
        topDao: TopDao,
        bestDao: TopBestDao,
        filmDao: FilmDao,
        api: KinopoiskApi
    ) = TopRepositoryImpl(topDao, bestDao, filmDao, api)
}