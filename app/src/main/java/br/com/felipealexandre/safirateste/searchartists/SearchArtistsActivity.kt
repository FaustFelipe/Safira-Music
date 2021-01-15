package br.com.felipealexandre.safirateste.searchartists

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import br.com.felipealexandre.safirateste.R
import kotlinx.android.synthetic.main.activity_search_artists.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchArtistsActivity : AppCompatActivity() {

    private val viewModel: SearchArtistsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_artists)

        configSearchView()
        configObservers()
    }

    private fun configSearchView() {
        edtSearchArtists?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val querySearch = v?.text.toString()
                    if (querySearch.isNotEmpty()) {
                        viewModel.getArtistsBySearch(querySearch)
                    }
                    return true
                }
                return false
            }
        })
    }

    private fun configObservers() {
        viewModel.listIdArtists.observe(this, {

        })
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