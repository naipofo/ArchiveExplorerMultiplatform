package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.responses

@kotlinx.serialization.Serializable
class HeadlinesResponse(
    val success: Boolean,
    val value: Posts,
)