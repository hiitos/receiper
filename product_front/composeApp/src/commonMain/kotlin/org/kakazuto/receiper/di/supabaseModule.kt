package org.kakazuto.receiper.di

import io.github.jan.supabase.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import org.kakazuto.receiper.BuildKonfig.SUPABASE_KEY
import org.kakazuto.receiper.BuildKonfig.SUPABASE_URL
import org.koin.dsl.module

val supabaseModule = module {
    single {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}