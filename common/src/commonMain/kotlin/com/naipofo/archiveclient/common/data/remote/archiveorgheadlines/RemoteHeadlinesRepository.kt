package com.naipofo.archiveclient.common.data.remote.archiveorgheadlines

import com.naipofo.archiveclient.common.data.model.SimpleHeadline
import com.naipofo.archiveclient.common.data.tryResult

class RemoteHeadlinesRepository(
    private val api: HeadlinesApi
) : HeadlinesRepository {
    override suspend fun loadHeadlines() =
        tryResult { api.getHeadlines().value.posts.map { SimpleHeadline(it.title, it.link) } }
}