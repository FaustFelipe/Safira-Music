package br.com.felipealexandre.safirateste.searchartists

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import br.com.felipealexandre.safirateste.R
import br.com.felipealexandre.safirateste.model.Artists
import br.com.felipealexandre.safirateste.model.RelatedArtists
import kotlinx.android.synthetic.main.activity_search_artists.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchArtistsActivity : AppCompatActivity() {

    private val viewModel: SearchArtistsViewModel by viewModel()

    private lateinit var adapterRecycler: ArtistsResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_artists)

        configSearchView()
        configRecyclerView()
        configObservers()
    }

    private fun configSearchView() {
        edtSearchArtists?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val querySearch = v?.text.toString()
                    if (querySearch.isNotEmpty()) {
                        closeKeyboardOnSearch()
                        adapterRecycler.clearList()
                        viewModel.getArtistsBySearch(querySearch)
                    }
                    return true
                }
                return false
            }
        })
    }

    private fun closeKeyboardOnSearch() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputManager?.let { it.hideSoftInputFromWindow(edtSearchArtists?.windowToken, 0) }
    }

    private fun configRecyclerView() {
        adapterRecycler = ArtistsResultAdapter(this)
        rvSearches?.apply {
            adapter = adapterRecycler
        }
    }

    private fun configObservers() {
        viewModel.id.observe(this, {
            if (it.isNullOrEmpty()) {
                // TODO("Mostrar erro")
            } else {
                viewModel.getArtist(it)
                viewModel.getRelatedArtists(it)
            }
        })
        viewModel.showLoading.observe(this, {
            hideOrShowRvLoading(it)
        })
        viewModel.artistSearched.observe(this, {
            adapterRecycler.setArtistSearched(it)
        })
        viewModel.artists.observe(this, {
            adapterRecycler.setRelatedArtists(it)
        })
    }

    private fun hideOrShowRvLoading(showLoading: Boolean) {
        progressBar?.visibility = when(showLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        rvSearches?.visibility = when(showLoading) {
            true -> View.GONE
            false -> View.VISIBLE
        }
    }

    companion object {
        fun open(activity: Activity?) {
            activity?.let {
                it.startActivity(Intent(it, SearchArtistsActivity::class.java))
                it.finish()
            }
        }
    }

}