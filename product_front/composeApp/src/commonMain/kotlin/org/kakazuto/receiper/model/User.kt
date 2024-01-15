package org.kakazuto.receiper.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_id")
    val userId: Int?,
    @SerialName("user_name")
    val name: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("created_at")
    val createdAt: String?
)