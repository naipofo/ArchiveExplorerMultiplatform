package com.naipofo.archiveclient.common.data.remote.archiveorgsearch.responses

import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.ResponseHeader

@kotlinx.serialization.Serializable
data class SearchResponseWrapper<T>(
    val responseHeader: ResponseHeader,
    val response: SearchResponse<T>
)
