package org.kakazuto.receiper.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("user_id")
    val userId: Int,
)