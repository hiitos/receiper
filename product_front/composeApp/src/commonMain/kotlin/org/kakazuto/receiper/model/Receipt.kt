package org.kakazuto.receiper.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Receipt(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("image_path")
    val imagePath: String
)