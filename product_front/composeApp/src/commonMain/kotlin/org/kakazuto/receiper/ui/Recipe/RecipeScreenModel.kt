package org.kakazuto.receiper.ui.Recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.seiko.imageloader.util.Logger
import kotlinx.coroutines.launch
import org.kakazuto.receiper.di.getScreenModel
import org.kakazuto.receiper.getPlatformName
import org.kakazuto.receiper.model.Recipe
import org.kakazuto.receiper.model.User
import org.kakazuto.receiper.net.RecipeApi
import org.kakazuto.receiper.net.UserApi
import org.kakazuto.receiper.utils.getUUID
import org.kakazuto.receiper.utils.setUUID
import org.kakazuto.receiper.utils.settings

class RecipeScreenModel(
    private val recipeApi: RecipeApi,
    private val userApi: UserApi
): ScreenModel {
    private val _recipe = mutableStateOf<Recipe?>(null)
    val recipe: State<Recipe?> = _recipe

    init {
        if (getUUID() == null) {
            userRegistration()
        } else {
            fetchRecipe()
        }
    }

    fun userRegistration() {
        coroutineScope.launch {
            kotlin.runCatching {
                setUUID(userApi.makeUser(
                    name= getPlatformName(),
                    email = "test@test.com"
                ))
            }.onSuccess {
                getUUID()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
    fun fetchRecipe() {
        coroutineScope.launch {
            kotlin.runCatching {
                recipeApi.fetchTheLatest(getUUID()!!)
            }.onSuccess {
                _recipe.value = it
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
