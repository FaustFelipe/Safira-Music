package br.com.felipealexandre.safirateste

import android.app.Application
import br.com.felipealexandre.safirateste.di.*
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class SafiraApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            apiModule,
            viewModelModule,
            repositoryModule,
            networkModule
        ))
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}