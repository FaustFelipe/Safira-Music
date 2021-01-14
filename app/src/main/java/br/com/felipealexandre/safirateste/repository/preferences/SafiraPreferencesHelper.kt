package br.com.felipealexandre.safirateste.repository.preferences

interface SafiraPreferencesHelper {

    fun setAccessToken(accessToken: String)

    fun getAccessToken(): String?

}