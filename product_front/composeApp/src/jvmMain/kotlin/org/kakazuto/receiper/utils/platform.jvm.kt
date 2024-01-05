package org.kakazuto.receiper.utils

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

class DesktopStorableImage(
    val imageBitmap: ImageBitmap
)

actual typealias PlatformStorableImage = DesktopStorableImage

actual fun createUUID(): String = UUID.randomUUID().toString()
