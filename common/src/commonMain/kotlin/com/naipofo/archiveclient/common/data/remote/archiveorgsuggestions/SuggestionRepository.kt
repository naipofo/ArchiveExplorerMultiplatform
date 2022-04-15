package com.naipofo.archiveclient.common.data.remote.archiveorgsuggestions

import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleSuggestion

interface SuggestionRepository{
    suspend fun getSuggestions(page: Int): Result<List<SimpleSuggestion>>
}