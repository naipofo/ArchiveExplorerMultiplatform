package com.naipofo.archiveclient.common.data.remote.archiveorgitem

import com.naipofo.archiveclient.common.data.model.SimpleArchiveItem
import com.naipofo.archiveclient.common.data.Result

interface ItemRepository {
    suspend fun getMetadata(identifier: String): Result<SimpleArchiveItem>
}