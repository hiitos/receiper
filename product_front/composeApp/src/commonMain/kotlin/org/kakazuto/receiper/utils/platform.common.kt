package org.kakazuto.receiper.utils

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher

expect class PlatformStorableImage

expect fun createUUID(): String

expect val ioDispatcher: CoroutineDispatcher

expect val isShareFeatureSupported: Boolean

val settings: Settings = Settings()
fun getUserId(): Int? {
    return settings.getIntOrNull("uuid")
}

fun setUserId(uuid: Int?) {
    settings.putInt("uuid", uuid ?: 0)
}

expect fun convertPlatformImageToByteArray(image: PlatformStorableImage): ByteArray
