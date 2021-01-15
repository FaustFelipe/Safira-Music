package br.com.felipealexandre.safirateste.searchartists

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.felipealexandre.safirateste.common.SingleLiveEvent
import br.com.felipealexandre.safirateste.model.RelatedArtists
import br.com.felipealexandre.safirateste.model.RelatedArtistsInfo
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

    val id = MutableLiveData<String>()
    val showLoading = MutableLiveData<Boolean>()
    val artistSearched = MutableLiveData<RelatedArtists>()
    val artists = MutableLiveData<RelatedArtists>()

    private val token by lazy {
        SafiraAppPreferencesHelper(context).getAccessToken()
    }

    fun getArtistsBySearch(query: String) {
        token?.let { accessToken ->
            showLoading.postValue(true)
            viewModelScope.launch(Dispatchers.IO) {
                val artistBySearch = repository.getArtistsBySearch(
                        accessToken,
                        query
                )
                val items = artistBySearch.body()?.artists?.items
                items?.let {
                    if (it.isNotEmpty())
                        id.postValue(it[0].id)
                    else {
                        id.postValue(null)
                        showLoading.postValue(false)
                    }
                }
            }
        }
    }

    fun getArtist(id: String) {
        token?.let { accessToken ->
            viewModelScope.launch(Dispatchers.IO) {
                val artist = repository.getArtist(
                    accessToken,
                    id
                )
                withContext(Dispatchers.Main) {
                    val isSuccess = artist.isSuccessful
                    if (isSuccess) {
                        val listRelated = ArrayList<RelatedArtistsInfo>()
                        artist.body()?.let { art -> listRelated.add(art) }
                        val relatedArtists = RelatedArtists(listRelated.toList())
                        artistSearched.postValue(relatedArtists)
                    } else {
                        artistSearched.postValue(null)
                        // TODO("Mostrar erro/motivo erro")
                    }
                }
            }
        }
    }

    fun getRelatedArtists(id: String) {
        token?.let { accessToken ->
            viewModelScope.launch(Dispatchers.IO) {
                val relatedArtists = repository.getRelatedArtists(
                    accessToken,
                    id
                )
                withContext(Dispatchers.Main) {
                    showLoading.postValue(false)
                    val isSuccess = relatedArtists.isSuccessful
                    if (isSuccess) {
                        artists.postValue(relatedArtists.body())
                    } else {
                        artists.postValue(null)
                        // TODO("Mostrar erro/motivo erro")
                    }
                }
            }
        }
    }

}