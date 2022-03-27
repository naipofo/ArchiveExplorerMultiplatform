package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines

import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleHeadline

interface HeadlinesRepository {
    suspend fun loadHeadlines(): Result<List<SimpleHeadline>>
}