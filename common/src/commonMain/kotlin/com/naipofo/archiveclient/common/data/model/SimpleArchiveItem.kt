package com.naipofo.archiveclient.common.data.model

data class SimpleArchiveItem(
    val identifier: String,
    val title: String,
    val description: String,
    val items: List<SimpleFile>
)

data class SimpleFile(
    val name:String,
    val format: String
)