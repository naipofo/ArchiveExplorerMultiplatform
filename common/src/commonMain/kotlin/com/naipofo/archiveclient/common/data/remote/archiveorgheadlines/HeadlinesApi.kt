package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines

import com.naipofo.archiveclient.common.data.remote.archiveorgheadlines.responses.HeadlinesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class HeadlinesApi(
    private val client: HttpClient
) {
    suspend fun getHeadlines(): HeadlinesResponse =
        client.get("https://archive.org/services/offshoot/home-page/announcements.php").body()
}