package br.com.felipealexandre.safirateste.model

import com.google.gson.annotations.SerializedName

data class RelatedArtists(
    @SerializedName("artists") val artists: List<RelatedArtistsInfo>
)

data class RelatedArtistsInfo(
    @SerializedName("external_urls") val externalUrls: ExternalUrls?,
    @SerializedName("followers") val followers: Followers,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("id") val id: String,
    @SerializedName("images") val images: List<ArtistsImages>,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("uri") val uri: String
)

data class ExternalUrls(
    @SerializedName("spotify") val spotify: String?
)

data class Followers(
    @SerializedName("href") val href: String?,
    @SerializedName("total") val total: Long
)

data class ArtistsImages(
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int
)
