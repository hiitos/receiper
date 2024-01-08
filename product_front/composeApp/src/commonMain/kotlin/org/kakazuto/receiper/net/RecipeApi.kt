package org.kakazuto.receiper.net

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import org.kakazuto.receiper.model.Recipe

interface RecipeApi {
    suspend fun fetchTheLatest(): Recipe
}

internal class RecipeApiImpl(
    private val client : SupabaseClient
): RecipeApi {
    private val table = client.postgrest["recipe"]

    override suspend fun fetchTheLatest(): Recipe {
        // get the latest 1 reciepe
        return table.select().decodeSingle()
    }
}