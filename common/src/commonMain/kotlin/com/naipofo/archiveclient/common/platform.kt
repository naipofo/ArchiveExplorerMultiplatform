package com.naipofo.archiveclient.common

import androidx.compose.ui.graphics.ImageBitmap

expect fun getPlatformName(): String

expect fun loadImage(bytes: ByteArray): ImageBitmap