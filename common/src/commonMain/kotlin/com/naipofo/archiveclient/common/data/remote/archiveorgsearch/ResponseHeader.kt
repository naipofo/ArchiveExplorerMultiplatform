package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

@kotlinx.serialization.Serializable
data class ResponseHeader (
    val status: Int,
    val QTime: Long,
)