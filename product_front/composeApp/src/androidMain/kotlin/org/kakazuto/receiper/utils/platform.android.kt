package org.kakazuto.receiper.utils

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

class AndroidStorableImage(
    val imageBitmap: ImageBitmap
)

actual typealias PlatformStorableImage = AndroidStorableImage

actual fun createUUID(): String = UUID.randomUUID().toString()