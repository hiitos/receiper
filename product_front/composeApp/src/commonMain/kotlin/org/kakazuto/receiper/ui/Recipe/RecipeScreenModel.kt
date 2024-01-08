package org.kakazuto.receiper.ui.Recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.launch
import org.kakazuto.receiper.di.getScreenModel
import org.kakazuto.receiper.model.Recipe
import org.kakazuto.receiper.net.RecipeApi

class RecipeScreenModel(
    private val recipeApi: RecipeApi
): ScreenModel {
    private val _recipe = mutableStateOf<Recipe?>(null)
    val recipe: State<Recipe?> = _recipe

    init {
        fetchRecipe()
    }
    fun fetchRecipe() {
        coroutineScope.launch {
            kotlin.runCatching {
                recipeApi.fetchTheLatest()
            }.onSuccess {
                _recipe.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
