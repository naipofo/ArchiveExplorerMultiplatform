package com.naipofo.archiveclient.common.ui.generic

import com.naipofo.archiveclient.common.data.RemoteResource
import com.naipofo.archiveclient.common.data.Result

inline fun <T> elementFromResource(
    resource: RemoteResource<Result<T>>,
    loading: () -> Unit,
    error: (e: Exception) -> Unit,
    content: (data: T) -> Unit,
) = when (resource) {
    RemoteResource.Loading -> loading()
    is RemoteResource.Success -> when (val result = resource.data) {
        is Result.Error -> error(result.exception)
        is Result.Success -> content(result.data)
    }
}