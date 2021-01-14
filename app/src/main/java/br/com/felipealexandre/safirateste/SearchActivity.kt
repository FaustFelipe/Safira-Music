package br.com.felipealexandre.safirateste

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    companion object {
        fun open(activity: Activity?) {
            activity?.let {
                it.startActivity(Intent(it, SearchActivity::class.java))
            }
        }
    }

}