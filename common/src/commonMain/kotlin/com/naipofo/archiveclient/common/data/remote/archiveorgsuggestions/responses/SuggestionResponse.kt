package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions.responses


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("value")
    val value: Value
)