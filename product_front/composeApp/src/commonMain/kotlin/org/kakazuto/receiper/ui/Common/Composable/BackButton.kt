package org.kakazuto.receiper.ui.Common.Composable

import androidx.compose.runtime.Composable
import org.kakazuto.receiper.ui.Common.Icon.IconCustomArrowBack

@Composable
fun BackButton(onClick: () -> Unit) {
    CircularButton(
        imageVector = IconCustomArrowBack,
        onClick = onClick
    )
}
