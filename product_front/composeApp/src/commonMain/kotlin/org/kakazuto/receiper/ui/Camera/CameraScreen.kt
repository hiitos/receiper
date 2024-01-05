package org.kakazuto.receiper.ui.Camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.kakazuto.receiper.di.getScreenModel
import org.kakazuto.receiper.ui.Common.Composable.BackButton
import org.kakazuto.receiper.ui.Common.Composable.TopLayout
import org.kakazuto.receiper.ui.Recipe.RecipeScreen

class CameraScreen(): Screen {
    @Composable
    override fun Content() {
        var showCamera by remember { mutableStateOf(false) }
        val scrennModel = getScreenModel<CameraScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        
        Box(Modifier.fillMaxSize().background(Color.Black)) {
            CameraView(Modifier.fill)
            TopLayout(
                alignLeftContent = {
                    BackButton {
                        navigator.push(RecipeScreen())
                    }
                },
                alignRightContent = {},
            )
        }    
    }
}

