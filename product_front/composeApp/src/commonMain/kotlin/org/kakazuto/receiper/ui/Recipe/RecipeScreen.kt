package org.kakazuto.receiper.ui.Recipe

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.kakazuto.receiper.di.getScreenModel


class RecipeScreen(): Screen {
    @Composable
    override fun Content() {
        val scrennModel = getScreenModel<RecipeScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        Text(text = "Recipe Screen is Displayed")
    }
}