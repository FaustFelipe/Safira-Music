package br.com.felipealexandre.safirateste.searchartists

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.felipealexandre.safirateste.R
import br.com.felipealexandre.safirateste.model.RelatedArtists
import br.com.felipealexandre.safirateste.model.RelatedArtistsInfo
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_artist_list.view.*
import java.lang.StringBuilder

class ArtistsResultAdapter(
    private val context: Context
): RecyclerView.Adapter<ArtistsResultAdapter.ViewHolder>() {

    private val artists = ArrayList<RelatedArtistsInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_artist_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = artists[position]
        Glide
            .with(context)
            .load(item.images[0].url)
            .into(holder.imgArtist)

        holder.txtArtistName.text = item.name
        val stringBuilder = StringBuilder()
        item.genres.forEach { genre -> stringBuilder.append(genre) }
        holder.txtArtistGenres.text = stringBuilder.toString()
        holder.txtArtistFollowers.text = "${item.followers.total.toString()} seguidores"

        holder.clArtistSearch.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.uri)))
        }
    }

    override fun getItemCount(): Int = artists.size

    fun clearList() {
        artists.clear()
        notifyDataSetChanged()
    }

    fun setArtistSearched(relatedArtists: RelatedArtists) {
        addArtist(relatedArtists)
    }

    fun setRelatedArtists(relatedArtists: RelatedArtists) {
        addArtist(relatedArtists)
    }

    private fun addArtist(relatedArtists: RelatedArtists) {
        artists.addAll(relatedArtists.artists)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val imgArtist: ImageView = itemView.imgArtist
        val txtArtistName: TextView = itemView.txtArtistName
        val txtArtistGenres: TextView = itemView.txtArtistGenres
        val txtArtistFollowers: TextView = itemView.txtArtistFollowers
        val clArtistSearch: ConstraintLayout = itemView.clArtistSearch

    }

}