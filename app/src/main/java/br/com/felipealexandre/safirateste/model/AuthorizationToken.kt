package br.com.felipealexandre.safirateste.model

import com.google.gson.annotations.SerializedName

data class AuthorizationToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int
)