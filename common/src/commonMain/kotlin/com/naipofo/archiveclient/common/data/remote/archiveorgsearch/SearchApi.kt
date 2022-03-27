package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.responses.SearchResponseWrapper
import com.naipofo.archiveclient.common.data.remote.archiveorgsearch.responses.SimpleSearchResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class SearchApi(
    private val client: HttpClient
) {
    suspend fun archiveSearch(
        query: FilterGroup, page: Long, sort: List<SortDefinition> = listOf(
            SortDefinition("downloads", SortType.DESC)
        ), rowsPerPage: Long = 50
    ): SearchResponseWrapper<SimpleSearchResponse> = client.get {
        url {
            protocol = URLProtocol.HTTPS
            host = "archive.org"
            path("advancedsearch.php")
            parameters.append("q", query.build())
            encodedParameters.append("output", "json")
            encodedParameters.append("page", page.toString())
            encodedParameters.append("rows", rowsPerPage.toString())
            listOf("identifier").forEach {
                encodedParameters.append("fl[]", it)
            }
            sort.map { it.build() }.forEach {
                encodedParameters.append("sort[]", it)
            }
            println(buildString())
        }
    }.body()
}