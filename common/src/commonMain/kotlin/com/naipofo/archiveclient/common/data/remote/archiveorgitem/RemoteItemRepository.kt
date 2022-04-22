package com.naipofo.archiveclient.common.data.remote.archiveorgitem

import com.naipofo.archiveclient.common.data.Result
import com.naipofo.archiveclient.common.data.model.SimpleArchiveItem
import com.naipofo.archiveclient.common.data.model.SimpleFile
import com.naipofo.archiveclient.common.data.tryResult

class RemoteItemRepository(
    private val api: ItemApi
) : ItemRepository {
    override suspend fun getMetadata(identifier: String): Result<SimpleArchiveItem> =
        tryResult {
            api.getMetadata(identifier).let {
                SimpleArchiveItem(
                    it.metadata.identifier,
                    it.metadata.title,
                    it.metadata.description ?: "no description",
                    items = it.files.map { file -> SimpleFile(file.name, file.format) })
            }
        }
}