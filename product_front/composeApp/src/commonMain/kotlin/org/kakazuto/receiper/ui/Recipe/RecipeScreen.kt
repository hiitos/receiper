package org.kakazuto.receiper.ui.Recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.kakazuto.receiper.di.getScreenModel
import org.kakazuto.receiper.ui.Camera.CameraScreen
import org.kakazuto.receiper.ui.Common.Composable.CircularButton


class RecipeScreen(): Screen {
    @Composable
    override fun Content() {
        val scrennModel = getScreenModel<RecipeScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            CircularButton(
                Icons.Filled.Add,
                modifier = Modifier.align(Alignment.BottomCenter).padding(36.dp),
                onClick = {
                    navigator.push(CameraScreen())
                },
            )
        }
    }
}