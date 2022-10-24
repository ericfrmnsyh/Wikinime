package com.dicoding.android.wikinime.core.di

import androidx.room.Room
import com.dicoding.android.wikinime.core.BuildConfig.*
import com.dicoding.android.wikinime.core.data.local.room.FavoriteAnimeDatabase
import com.dicoding.android.wikinime.core.data.remote.RemoteDataSource
import com.dicoding.android.wikinime.core.data.remote.network.ApiService
import com.dicoding.android.wikinime.core.domain.repository.IAnimeRepository
import com.dicoding.android.wikinime.core.data.AnimeRepository
import com.dicoding.android.wikinime.core.data.local.LocalDataSource
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FavoriteAnimeDatabase>().favoriteAnimeDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(PASSPHRASE.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FavoriteAnimeDatabase::class.java,
            FAVORITE_DB
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor =
            if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val hostname = HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, CERT_PIN_1)
            .add(hostname, CERT_PIN_2)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(loggingInterceptor))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IAnimeRepository> { AnimeRepository(get(), get()) }
}