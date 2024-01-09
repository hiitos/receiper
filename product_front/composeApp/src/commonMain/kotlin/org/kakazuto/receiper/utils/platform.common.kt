package org.kakazuto.receiper.utils

import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineDispatcher

expect class PlatformStorableImage

expect fun createUUID(): String

expect val ioDispatcher: CoroutineDispatcher

expect val isShareFeatureSupported: Boolean

val settings: Settings = Settings()
fun getUUID(): Int? {
    return settings.getIntOrNull("uuid")
}

fun setUUID(uuid: Int?) {
    settings.putInt("uuid", uuid ?: 0)
}
