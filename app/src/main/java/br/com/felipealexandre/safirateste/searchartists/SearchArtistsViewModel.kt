package br.com.felipealexandre.safirateste.searchartists

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.felipealexandre.safirateste.repository.SafiraRepositoryImpl
import br.com.felipealexandre.safirateste.repository.preferences.SafiraAppPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

class SearchArtistsViewModel(
        private val context: Context,
        private val repository: SafiraRepositoryImpl
): ViewModel() {

    val listIdArtists = MutableLiveData<List<String>>()

    fun getArtistsBySearch(query: String) {
        val token = SafiraAppPreferencesHelper(context).getAccessToken()
        token?.let { accessToken ->
            viewModelScope.launch(Dispatchers.IO) {
                val artistBySearch = repository.getArtistsBySearch(
                        accessToken,
                        query
                )
                val items = artistBySearch.body()?.artists?.items
                items?.let {
                    val listIds = arrayListOf<String>()
                    it.forEach { item ->
                           listIds.add(item.id)
                    }
                    min(listIds.size, 50)
                    listIdArtists.postValue(listIds)
                }
            }
        }
    }

}