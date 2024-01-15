package org.kakazuto.receiper.ui.Recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.kakazuto.receiper.getPlatformName
import org.kakazuto.receiper.model.Recipe
import org.kakazuto.receiper.net.RecipeApi
import org.kakazuto.receiper.net.UserApi
import org.kakazuto.receiper.utils.getUserId
import org.kakazuto.receiper.utils.setUserId

class RecipeScreenModel(
    private val recipeApi: RecipeApi,
    private val userApi: UserApi,
): ScreenModel {
    private val _recipe = mutableStateOf<Recipe?>(null)
    val recipe: State<Recipe?> = _recipe

    init {
        if (getUserId() == null) {
            userRegistration()
        } else {
            fetchRecipe()
        }
    }

    fun userRegistration() {
        screenModelScope.launch {
            kotlin.runCatching {
                setUserId(userApi.makeUser(
                    name= getPlatformName(),
                    email = "test@test.com"
                ))
            }.onSuccess {
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
    fun fetchRecipe() {
        screenModelScope.launch {
            kotlin.runCatching {
                recipeApi.fetchTheLatest(getUserId()!!)
            }.onSuccess {
                _recipe.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
