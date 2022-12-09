package com.vahitkeskin.jetpackcomposewikipediaclone.di

import android.content.Context
import androidx.room.Room
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.google.gson.GsonBuilder
import com.vahitkeskin.jetpackcomposewikipediaclone.BuildConfig
import com.vahitkeskin.jetpackcomposewikipediaclone.api.FavoriteDatabase
import com.vahitkeskin.jetpackcomposewikipediaclone.api.LastSearchDatabase
import com.vahitkeskin.jetpackcomposewikipediaclone.api.WikipediaAPI
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.favorites.FavoriteDao
import com.vahitkeskin.jetpackcomposewikipediaclone.dao.lastsearches.LastSearchDao
import com.vahitkeskin.jetpackcomposewikipediaclone.repository.*
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.BASE_URL
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.FAVORITE_SCREEN_TABLE_NAME
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.Constants.Companion.LAST_SEARCH_TABLE_NAME
import com.vahitkeskin.jetpackcomposewikipediaclone.utils.FlipperNetworkObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @authot: Vahit Keskin
 * creared on 1.12.2022
 */

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class AppModule {

    //Favorite
    @Provides
    fun provideFavoriteDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java,
        FAVORITE_SCREEN_TABLE_NAME
    ).build()

    @Provides
    fun provideFavoriteDao(
        lastSearchDatabase: FavoriteDatabase
    ) = lastSearchDatabase.favoriteDao()

    @Provides
    fun provideFavoriteRepository(
        favoriteDao: FavoriteDao
    ): FavoriteRepository = FavoriteRepositoryImpl(
        favoriteDao = favoriteDao
    )

    //LastSearch
    @Provides
    fun provideLastSearchDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        LastSearchDatabase::class.java,
        LAST_SEARCH_TABLE_NAME
    ).build()

    @Provides
    fun provideLastSearchDao(
        lastSearchDatabase: LastSearchDatabase
    ) = lastSearchDatabase.lastSearchDao()

    @Provides
    fun provideLastSearchRepository(
        lastSearchDao: LastSearchDao
    ): LastSearchRepository = LastSearchRepositoryImpl(
        lastSearchDao = lastSearchDao
    )

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): Context {
        return app
    }

    //MainPage
    /*
    @Singleton
    @Provides
    fun injectRetrofitAPI() : WikipediaAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(WikipediaAPI::class.java)
    }
    */

    @Provides
    @Singleton
    internal fun provideCache(context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        cache: Cache
    ): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpClientBuilder.cache(cache)
        okHttpClientBuilder.addInterceptor(headerInterceptor)


        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor {
            val requestBuilder = it.request().newBuilder()
            //hear you can add all headers you want by calling 'requestBuilder.addHeader(name ,  value)'
            it.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client().build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun client(): OkHttpClient.Builder {
        return if (BuildConfig.DEBUG && FlipperNetworkObject.networkFlipperPlugin != null) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(FlipperOkhttpInterceptor(FlipperNetworkObject.networkFlipperPlugin))
        } else {
            OkHttpClient.Builder()
        }
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): WikipediaAPI {
        return retrofit.create(WikipediaAPI::class.java)
    }
}