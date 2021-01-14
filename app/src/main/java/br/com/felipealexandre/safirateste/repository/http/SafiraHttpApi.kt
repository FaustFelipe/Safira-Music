package br.com.felipealexandre.safirateste.repository.http

import br.com.felipealexandre.safirateste.model.AuthorizationToken
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface SafiraHttpApi {

    @FormUrlEncoded
    @POST
    suspend fun getToken(
        @Url url: String,
        @Header(AUTHORIZATION) value: String,
        @Field(GRANT_TYPE) grantType: String = CLIENT_CREDENTIALS
    ): Response<AuthorizationToken>

    companion object {
        const val WEB_TOKEN = "token"

        private const val AUTHORIZATION = "Authorization"
        private const val GRANT_TYPE = "grant_type"
        private const val CLIENT_CREDENTIALS = "client_credentials"
    }

}