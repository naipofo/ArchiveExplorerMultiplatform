package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions

import com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions.responses.SuggestionResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class SuggestionApi(
    private val client: HttpClient,
) {
    suspend fun getSuggestions(page: Int, count: Int): SuggestionResponse =
        client.get("https://archive.org/services/offshoot/home-page/collections.php?page=$page&count=$count").body()
}