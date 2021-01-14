package br.com.felipealexandre.safirateste.repository

import br.com.felipealexandre.safirateste.common.ApiKeyBase64
import br.com.felipealexandre.safirateste.model.AuthorizationToken
import br.com.felipealexandre.safirateste.repository.http.SafiraHttpApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Response

class SafiraRepository(
    private val service: SafiraHttpApi
) {

    suspend fun getTokenAsync(): Response<AuthorizationToken> {
        return service.getToken(
                "https://accounts.spotify.com/api/${SafiraHttpApi.WEB_TOKEN}",
                "Basic ${ApiKeyBase64.getApiKeyEncoded()}"
        )
    }

}
