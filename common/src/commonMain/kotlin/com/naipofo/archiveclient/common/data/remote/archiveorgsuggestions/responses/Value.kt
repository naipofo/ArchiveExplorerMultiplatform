package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Value(
    @SerialName("docs")
    val docs: List<Doc>
)