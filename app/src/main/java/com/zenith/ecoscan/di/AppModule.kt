package com.zenith.ecoscan.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zenith.ecoscan.BuildConfig
import com.zenith.ecoscan.data.api.ApiService
import com.zenith.ecoscan.data.local.DataDao
import com.zenith.ecoscan.data.local.DataDatabase
import com.zenith.ecoscan.domain.DataInteractor
import com.zenith.ecoscan.domain.DataUseCase
import com.zenith.ecoscan.domain.IDataRepository
import com.zenith.ecoscan.data.Repository
import com.zenith.ecoscan.data.api.NetworkDataSource
import com.zenith.ecoscan.data.local.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUrl() = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setLenient().create()

    @Provides
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context): DataDatabase {
        return Room.databaseBuilder(context, DataDatabase::class.java, "history_data").build()
    }

    @Provides
    @Singleton
    fun provideDataDao(dataDatabase: DataDatabase) : DataDao {
        return dataDatabase.dataDao()
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(apiService: ApiService) : NetworkDataSource {
        return NetworkDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(dataDao: DataDao) : LocalDataSource {
        return LocalDataSource(dataDao)
    }

    @Provides
    @Singleton
    fun provideIDataRepository(localDataSource: LocalDataSource, networkDataSource: NetworkDataSource) : IDataRepository {
        return Repository(localDataSource, networkDataSource)
    }

    @Provides
    @Singleton
    fun provideDataUseCase(repository: IDataRepository) : DataUseCase {
        return DataInteractor(repository)
    }

    private fun provideOKHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(loggingInterceptor)

        clientBuilder.build()
    } else {
        OkHttpClient.Builder().build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(url: String, gson: Gson) : ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOKHttpClient())
            .build()
            .create(ApiService::class.java)
}