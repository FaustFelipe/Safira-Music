package br.com.felipealexandre.safirateste.di

import br.com.felipealexandre.safirateste.BuildConfig
import br.com.felipealexandre.safirateste.R
import br.com.felipealexandre.safirateste.repository.SafiraRepositoryImpl
import br.com.felipealexandre.safirateste.repository.http.SafiraHttpApi
import br.com.felipealexandre.safirateste.searchartists.SearchArtistsViewModel
import br.com.felipealexandre.safirateste.splash.SplashViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    fun provideSafiraApi(retrofit: Retrofit): SafiraHttpApi {
        return retrofit.create(SafiraHttpApi::class.java)
    }

    single { provideSafiraApi(get()) }

}

val networkModule = module {

    val connectTimeout: Long = 60
    val readTimeout: Long = 60

    fun provideHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }
        return okHttpClient.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        val baseUrl = androidContext().getString(R.string.BASE_URL)
        provideRetrofit(get(), baseUrl)
    }

}

val repositoryModule = module {

    fun provideSafiraRepository(service: SafiraHttpApi): SafiraRepositoryImpl {
        return SafiraRepositoryImpl(service)
    }

    single { provideSafiraRepository(get()) }

}

val viewModelModule = module {

    viewModel {
        SplashViewModel(context = androidContext(),repository = get())
    }

    viewModel {
        SearchArtistsViewModel(context = androidContext(), repository = get())
    }

}
