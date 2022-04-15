package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Doc(
    @SerialName("identifier")
    val identifier: String,
    @SerialName("item_count")
    val itemCount: String,
    @SerialName("title")
    val title: String
)