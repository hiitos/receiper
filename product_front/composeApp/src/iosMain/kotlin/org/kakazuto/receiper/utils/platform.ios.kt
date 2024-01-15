package org.kakazuto.receiper.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.CoreFoundation.CFUUIDCreate
import platform.CoreFoundation.CFUUIDCreateString
import platform.Foundation.CFBridgingRelease
import platform.UIKit.UIImage
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

class IosStorableImage(
    val rawValue: UIImage
)

actual typealias PlatformStorableImage = IosStorableImage

@OptIn(ExperimentalForeignApi::class)
actual fun createUUID(): String =
    CFBridgingRelease(CFUUIDCreateString(null, CFUUIDCreate(null))) as String

actual val ioDispatcher = Dispatchers.IO

actual val isShareFeatureSupported: Boolean = true

actual fun convertPlatformImageToByteArray(image: PlatformStorableImage): ByteArray {
    return image.rawValue.toImageByteArray()
}

@OptIn(ExperimentalForeignApi::class)
fun UIImage.toImageByteArray(): ByteArray {
    val jpgRepresentation = UIImageJPEGRepresentation(this, 1.0)!!
    val byteArray = ByteArray(jpgRepresentation.length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), jpgRepresentation.bytes, jpgRepresentation.length)
        }
    }
    return byteArray
}
