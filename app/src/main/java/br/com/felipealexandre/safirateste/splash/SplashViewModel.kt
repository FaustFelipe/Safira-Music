package br.com.felipealexandre.safirateste.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.felipealexandre.safirateste.common.SingleLiveEvent
import br.com.felipealexandre.safirateste.repository.SafiraRepositoryImpl
import br.com.felipealexandre.safirateste.repository.preferences.SafiraAppPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val context: Context,
    private val repository: SafiraRepositoryImpl
): ViewModel() {

    val showError = SingleLiveEvent<String>()

    // O correto seria criar um serviço em background para apenas carregar um novo token se o mesmo
    // estivesse expirado
    fun loadToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val authorizationToken = repository.getTokenAsync()
            val isSuccess = authorizationToken.isSuccessful
            withContext(Dispatchers.Main) {
                if (isSuccess) {
                    authorizationToken.body()?.let {
                        SafiraAppPreferencesHelper(context).setAccessToken(it.accessToken)
                    }
                    showError.value = null
                } else {
                    showError.value = "Erro"
                }
            }
        }
    }

}