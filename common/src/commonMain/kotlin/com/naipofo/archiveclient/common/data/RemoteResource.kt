package com.naipofo.archiveclient.common.data

sealed class RemoteResource<out R> {
    object Loading : RemoteResource<Nothing>()
    data class Success<out T>(val data: T) : RemoteResource<T>()
}