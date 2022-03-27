package com.naipofo.archiveclient.common.data.remote.archiveorgsearch.responses

@kotlinx.serialization.Serializable
data class SearchResponse<T>(
    val numFound : Long,
    val start: Long,
    val docs: List<T>
)