package org.kakazuto.receiper.ui.Camera

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.kakazuto.receiper.net.ReceiptApi
import org.kakazuto.receiper.utils.PlatformStorableImage
import org.kakazuto.receiper.utils.convertPlatformImageToByteArray
import org.kakazuto.receiper.utils.getUserId

class CameraScreenModel(
    private val receiptApi: ReceiptApi
): ScreenModel {
    init {
    }

    fun onCapture(image: PlatformStorableImage) {
        screenModelScope.launch {
            kotlin.runCatching {
                SendToSupabase(convertPlatformImageToByteArray(image))
            }
        }
    }

    fun SendToSupabase(receipt: ByteArray) {
        screenModelScope.launch {
            kotlin.runCatching {
                receiptApi.uploadReceipt(getUserId()!!, receipt )
            }.onSuccess {
                receiptApi.setPathOnDB(getUserId()!!, it!!)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }


}