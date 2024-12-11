package bob.colbaskin.hackatontemplate.di

import android.content.Context
import bob.colbaskin.hackatontemplate.analytics.data.AnalyticRepositoryImpl
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticApiService
import bob.colbaskin.hackatontemplate.analytics.domain.AnalyticRepository
import bob.colbaskin.hackatontemplate.auth.data.AuthRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.data.AuthDataStoreRepositoryImpl
import bob.colbaskin.hackatontemplate.auth.domain.local.AuthDataStoreRepository
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthApiService
import bob.colbaskin.hackatontemplate.auth.domain.network.AuthRepository
import bob.colbaskin.hackatontemplate.onBoarding.data.OnBoardingDataStoreRepositoryImpl
import bob.colbaskin.hackatontemplate.onBoarding.domain.OnBoardingDataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideOnBoardingDataStoreRepository(
        @ApplicationContext context: Context
    ): OnBoardingDataStoreRepository {
        return OnBoardingDataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataStoreRepository: AuthDataStoreRepository,
        authApiService: AuthApiService
    ): AuthRepository {
        return AuthRepositoryImpl(authDataStoreRepository, authApiService)
    }

    @Provides
    @Singleton
    fun provideTokenDataStoreRepository(
        @ApplicationContext context: Context
    ): AuthDataStoreRepository {
        return AuthDataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAnalyticApiService(retrofit: Retrofit): AnalyticApiService {
        return retrofit.create(AnalyticApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnalyticsRepository(
        analyticApiService: AnalyticApiService,
        @ApplicationContext context: Context
    ): AnalyticRepository {
        return AnalyticRepositoryImpl(analyticApiService, context)
    }
}