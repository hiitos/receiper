package org.kakazuto.receiper.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import kotlinx.coroutines.Dispatchers
import java.io.ByteArrayOutputStream
import java.util.UUID

class AndroidStorableImage(
    val imageBitmap: ImageBitmap
)

actual typealias PlatformStorableImage = AndroidStorableImage

actual fun createUUID(): String = UUID.randomUUID().toString()

actual val ioDispatcher = Dispatchers.IO

actual val isShareFeatureSupported: Boolean = true

actual fun convertPlatformImageToByteArray(image: PlatformStorableImage): ByteArray {
    ByteArrayOutputStream().apply {
        image.imageBitmap.asAndroidBitmap().compress(
            android.graphics.Bitmap.CompressFormat.JPEG,
            100,
            this
        )
        return toByteArray()
    }
}

