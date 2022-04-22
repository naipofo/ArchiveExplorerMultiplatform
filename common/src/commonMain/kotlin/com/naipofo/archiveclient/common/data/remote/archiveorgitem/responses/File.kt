package com.naipofo.archiveclient.common.data.remote.archiveorgitem.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class File(
    @SerialName("format")
    val format: String,
    @SerialName("mtime")
    val mtime: String? = null,
    @SerialName("name")
    val name: String,
    @SerialName("size")
    val size: String? = null
)