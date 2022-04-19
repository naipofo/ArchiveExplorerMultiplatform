package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions

import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleSuggestion
import com.naipofo.archiveclient.common.data.tryResult

class RemoteSuggestionRepository(
    private val api: SuggestionApi
): SuggestionRepository {
    private val count = 50
    override suspend fun getSuggestions(page: Int): Result<List<SimpleSuggestion>> = tryResult {
        api.getSuggestions(page, count).value.docs.map { SimpleSuggestion(it.title, it.identifier, it.itemCount.toLongOrNull() ?: 0) }
    }
}