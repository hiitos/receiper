package org.kakazuto.receiper.utils

import androidx.compose.ui.graphics.ImageBitmap

class AndroidStorableImage(
    val imageBitmap: ImageBitmap
)

actual typealias PlatformStorableImage = AndroidStorableImage

actual fun createUUID(): String = ""