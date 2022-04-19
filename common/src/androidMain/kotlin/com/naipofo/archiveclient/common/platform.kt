package com.naipofo.archiveclient.common

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun getPlatformName(): String {
    return "Android"
}

actual fun loadImage(bytes: ByteArray): ImageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()