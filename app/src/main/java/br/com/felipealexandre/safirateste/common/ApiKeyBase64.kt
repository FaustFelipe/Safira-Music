package br.com.felipealexandre.safirateste.common

import android.util.Base64
import br.com.felipealexandre.safirateste.BuildConfig

object ApiKeyBase64 {

    fun getApiKeyEncoded(): String {
        val clientId = BuildConfig.CLIENT_ID
        val cliendSecret = BuildConfig.CLIENT_SECRET
        val apiKeyToEncode = "$clientId:$cliendSecret"
        return Base64.encodeToString(apiKeyToEncode.toByteArray(), Base64.NO_WRAP)
    }

}