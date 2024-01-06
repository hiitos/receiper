package org.kakazuto.receiper.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.russhwolf.settings.Settings
import io.github.irgaly.kottage.KottageEnvironment
import kotlinx.coroutines.CoroutineDispatcher

expect class PlatformStorableImage

expect fun createUUID(): String

expect val ioDispatcher: CoroutineDispatcher

expect val isShareFeatureSupported: Boolean

val settings: Settings = Settings()
fun getUUID(): String? {
    if (settings.getStringOrNull("uuid") == null) {
        val uuid = createUUID()
        settings.putString("uuid", uuid)
        return uuid
    } else {
        return settings.getStringOrNull("uuid")
    }
}
