package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

import com.naipofo.archiveclient.common.data.model.SimpleSearchResult
import com.naipofo.archiveclient.common.data.tryResult

class RemoteSearchRepository(
    private val api: SearchApi
) : SearchRepository {
    override suspend fun simpleSearch(query: String) = tryResult {
        api.archiveSearch(
            FilterGroup.Single(SearchFilter(field = "title", filter = SearchRule.Exact(query))), 1
        ).response.docs.map { SimpleSearchResult(it.identifier) }
    }
}