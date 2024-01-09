package org.kakazuto.receiper.net

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import org.kakazuto.receiper.model.Recipe

interface RecipeApi {
    suspend fun fetchTheLatest(userId: Int): Recipe
}

internal class RecipeApiImpl(
    private val client : SupabaseClient
): RecipeApi {
    private val table = client.postgrest["recipe"]

    override suspend fun fetchTheLatest(userId: Int): Recipe {
        // filter my recipe
        val yourRecipe = table.select{
            eq("user_id", userId)
            order("created_at", Order.DESCENDING, false )
        }.decodeList<Recipe>()
        // get the latest one
        val yourFirstrecipe = yourRecipe.first()
        return yourFirstrecipe
    }
}