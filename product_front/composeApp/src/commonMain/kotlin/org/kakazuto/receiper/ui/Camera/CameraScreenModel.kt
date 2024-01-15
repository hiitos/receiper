package org.kakazuto.receiper.ui.Camera

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.launch
import org.kakazuto.receiper.net.ReceiptApi
import org.kakazuto.receiper.utils.PlatformStorableImage
import org.kakazuto.receiper.utils.convertPlatformImageToByteArray
import org.kakazuto.receiper.utils.getUUID
import org.kakazuto.receiper.utils.settings

class CameraScreenModel(
    private val receiptApi: ReceiptApi
): ScreenModel {
    init {
    }

    fun onCapture(image: PlatformStorableImage) {
        coroutineScope.launch {
            kotlin.runCatching {
                SendToSupabase(convertPlatformImageToByteArray(image))
            }
        }
    }

    fun SendToSupabase(receipt: ByteArray) {
        coroutineScope.launch {
            kotlin.runCatching {
                receiptApi.uploadReceipt(getUUID()!!, receipt )
            }
        }
    }


}