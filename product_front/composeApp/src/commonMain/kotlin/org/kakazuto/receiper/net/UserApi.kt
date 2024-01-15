package org.kakazuto.receiper.net

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import org.kakazuto.receiper.model.User
import org.kakazuto.receiper.utils.createUUID
import kotlin.random.Random

interface UserApi {
    suspend fun makeUser(name: String, email: String): Int?
}

internal class UserApiImpl(
    private val client : SupabaseClient,
): UserApi {
    private val table = client.postgrest["user"]

    override suspend fun makeUser(
        name: String,
        email: String
    ): Int {
        val user = User
        // create random int
        val userId = Random.nextInt(10000)
        val result = table.insert(User(userId,name,email,null)).decodeSingle<User>()
        // make the user
        return userId
    }
}