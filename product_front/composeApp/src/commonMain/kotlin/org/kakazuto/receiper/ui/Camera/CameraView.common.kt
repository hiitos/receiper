package org.kakazuto.receiper.ui.Camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.kakazuto.receiper.model.PictureData
import org.kakazuto.receiper.utils.PlatformStorableImage

@Composable
expect fun CameraView(
    modifier: Modifier,
    onCapture: (image: PlatformStorableImage) -> Unit,
)

expect fun ByteArray.toImageBitmap(): ImageBitmap
