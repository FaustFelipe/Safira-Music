package br.com.felipealexandre.safirateste.repository.http

import br.com.felipealexandre.safirateste.model.Artists
import br.com.felipealexandre.safirateste.model.AuthorizationToken
import br.com.felipealexandre.safirateste.model.RelatedArtists
import br.com.felipealexandre.safirateste.model.RelatedArtistsInfo
import retrofit2.Response
import retrofit2.http.*

interface SafiraHttpApi {

    @GET(WEB_SEARCH)
    suspend fun getArtistsBySearch(
        @Header(AUTHORIZATION) tokenAuthorization: String,
        @Query("q") search: String,
        @Query("type") type: String
    ): Response<Artists>

    @GET(WEB_ARTISTS)
    suspend fun getArtist(
        @Header(AUTHORIZATION) tokenAuthorization: String,
        @Path(PATH_ID) id: String
    ): Response<RelatedArtistsInfo>

    @GET(WEB_ARTISTS_RELATED)
    suspend fun getRelatedArtists(
        @Header(AUTHORIZATION) tokenAuthorization: String,
        @Path(PATH_ID) id: String
    ): Response<RelatedArtists>

    @FormUrlEncoded
    @POST
    suspend fun postToGetToken(
        @Url url: String,
        @Header(AUTHORIZATION) value: String,
        @Field(GRANT_TYPE) grantType: String = CLIENT_CREDENTIALS
    ): Response<AuthorizationToken>

    companion object {
        private const val PATH_ID = "id"

        const val WEB_SEARCH = "search"
        const val WEB_ARTISTS = "artists/{$PATH_ID}"
        const val WEB_ARTISTS_RELATED = "artists/{$PATH_ID}/related-artists"
        const val WEB_TOKEN = "token"

        private const val AUTHORIZATION = "Authorization"
        private const val GRANT_TYPE = "grant_type"
        private const val CLIENT_CREDENTIALS = "client_credentials"
    }

}