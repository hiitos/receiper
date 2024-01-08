package org.kakazuto.receiper.di

import org.kakazuto.receiper.net.RecipeApi
import org.kakazuto.receiper.net.RecipeApiImpl
import org.koin.dsl.module

val netModule = module {
    single<RecipeApi> { RecipeApiImpl(get()) }
}