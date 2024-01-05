package org.kakazuto.receiper.ui.Camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.kakazuto.receiper.model.PictureData
import org.kakazuto.receiper.utils.PlatformStorableImage

@Composable
actual fun CameraView(
    modifier: Modifier,
    onCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit
) {

}