package com.naipofo.archiveclient.common.data.remote.archiveorgitem.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("contributor")
    val contributor: List<String>,
    @SerialName("description")
    val description: String? = null,
    @SerialName("identifier")
    val identifier: String,
    @SerialName("mediatype")
    val mediatype: String,
    @SerialName("title")
    val title: String
)