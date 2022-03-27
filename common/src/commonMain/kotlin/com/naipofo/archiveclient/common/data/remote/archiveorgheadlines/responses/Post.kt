package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.responses

@kotlinx.serialization.Serializable
class Post(
    val title: String,
    val link: String
)