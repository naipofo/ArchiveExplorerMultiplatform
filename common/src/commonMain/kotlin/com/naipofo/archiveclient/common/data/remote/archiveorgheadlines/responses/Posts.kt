package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.responses

@kotlinx.serialization.Serializable
data class Posts(
    val posts: List<Post>
)