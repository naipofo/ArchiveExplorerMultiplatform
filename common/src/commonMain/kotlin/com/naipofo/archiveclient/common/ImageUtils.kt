package com.naipofo.archiveclient.common

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun loadFromUrl(url: String, client: HttpClient) = loadImage(client.get(url).readBytes())

//TODO: Better local cashing
val cache: HashMap<String, ImageBitmap> = hashMapOf()

suspend fun cashedImageFromUrl(url: String, client: HttpClient): ImageBitmap? {
    return try {
        cache.getOrElse(url) {
            val img = loadFromUrl(url, client)
            cache[url] = img
            img
        }
    } catch (_: Exception){
        null
    }
}
