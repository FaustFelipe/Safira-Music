package br.com.felipealexandre.safirateste.model

import com.google.gson.annotations.SerializedName

data class Artists(
    @SerializedName("artists") val artists: ArtistsItems
)

data class ArtistsItems(
    @SerializedName("items") val items: List<ArtistsItemsInformantion>
)

data class ArtistsItemsInformantion(
	@SerializedName("id") val id: String
)
