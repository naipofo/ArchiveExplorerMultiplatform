package com.naipofo.archiveclient.common.data.remote.archiveorgitem.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetadataResponse(
    @SerialName("created")
    val created: Int,
    @SerialName("files")
    val files: List<File>,
    @SerialName("item_last_updated")
    val itemLastUpdated: Int,
    @SerialName("item_size")
    val itemSize: Int,
    @SerialName("metadata")
    val metadata: Metadata,
)