package com.naipofo.archiveclient.common.data.remote.archiveorgsearch

import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleSearchResult

interface SearchRepository {
    suspend fun simpleSearch(query: String): Result<List<SimpleSearchResult>>
}