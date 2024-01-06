package org.kakazuto.receiper.ui.Camera


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.kakazuto.receiper.di.getScreenModel

class CameraScreen(): Screen {
    @Composable
    override fun Content() {
        val scrennModel = getScreenModel<CameraScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        Text(text = "Camera Screen is Displayed")
        CameraView(
            modifier = Modifier.fillMaxSize(),
            onCapture = { picture, image ->
//                scrennModel.onCapture(picture, image)
                navigator.pop()
            })
    }
}