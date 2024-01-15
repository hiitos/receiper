package org.kakazuto.receiper


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import org.kakazuto.receiper.di.initKoin
import org.kakazuto.receiper.theme.AppTheme
import org.kakazuto.receiper.ui.Camera.CameraScreen
import org.kakazuto.receiper.ui.Recipe.RecipeScreen

@Composable
internal fun App() = AppTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        initKoin()
        val screens = listOf(RecipeScreen())
        Navigator(screens)
    }
}

internal expect fun openUrl(url: String?)

expect fun getPlatformName(): String