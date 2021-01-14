package br.com.felipealexandre.safirateste.repository.preferences

import android.content.Context

class SafiraAppPreferencesHelper(
        ctx: Context,
        prefFileName: String = "safira_prefs"
): SafiraPreferencesHelper {

    private val prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun setAccessToken(accessToken: String) {
        prefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply()
    }

    override fun getAccessToken(): String? {
        return prefs
                .getString(PREF_KEY_ACCESS_TOKEN, null)
    }

    companion object {
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }

}