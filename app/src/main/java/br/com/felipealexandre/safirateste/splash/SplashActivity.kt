package br.com.felipealexandre.safirateste.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.felipealexandre.safirateste.R
import br.com.felipealexandre.safirateste.SearchActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.loadToken()
        viewModel.showError.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                SearchActivity.open(this)
            } else {
                TODO("Mostrar mensagem de erro")
            }
        })
    }

}