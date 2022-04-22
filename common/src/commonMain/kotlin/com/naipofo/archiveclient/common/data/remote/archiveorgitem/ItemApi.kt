package com.naipofo.archiveclient.common.data.remote.archiveorgitem

import com.naipofo.archiveclient.common.data.remote.archiveorgitem.responses.MetadataResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ItemApi(
    private val client: HttpClient
) {
    suspend fun getMetadata(identifier: String):MetadataResponse =
        client.get("https://archive.org/metadata/$identifier").body()
}