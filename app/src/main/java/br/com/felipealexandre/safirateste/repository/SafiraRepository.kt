package br.com.felipealexandre.safirateste.repository

import br.com.felipealexandre.safirateste.common.ApiKeyBase64
import br.com.felipealexandre.safirateste.model.Artists
import br.com.felipealexandre.safirateste.model.AuthorizationToken
import br.com.felipealexandre.safirateste.repository.http.SafiraHttpApi
import br.com.felipealexandre.safirateste.repository.preferences.SafiraAppPreferencesHelper
import retrofit2.Response

interface SafiraRepository {
    suspend fun getArtistsBySearch(token: String, query: String): Response<Artists>
    suspend fun getTokenAsync(): Response<AuthorizationToken>
}

class SafiraRepositoryImpl(
    private val service: SafiraHttpApi
): SafiraRepository {

    override suspend fun getArtistsBySearch(token: String, query: String): Response<Artists> {
        return service.getArtistsBySearch(
            "Bearer $token",
            query,
            "artist"
        )
    }

    override suspend fun getTokenAsync(): Response<AuthorizationToken> {
        return service.postToGetToken(
            "https://accounts.spotify.com/api/${SafiraHttpApi.WEB_TOKEN}",
            "Basic ${ApiKeyBase64.getApiKeyEncoded()}"
        )
    }

}
