package com.kosmos.components.kosmoscomponents

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A customizable glass-style container for Jetpack Compose.
 *
 * Provides a translucent surface with an optional border
 * and customizable shape.
 *
 * Place any composable content inside this container.
 *
 * Note: This component draws a translucent surface; it does not
 * automatically blur the content behind it.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param shape Shape of the glass card.
 * @param backgroundColor Background color of the card.
 * @param backgroundAlpha Transparency of the background color.
 * @param borderColor Color of the card border.
 * @param borderWidth Thickness of the card border.
 * @param contentPadding Padding applied around the content.
 * @param content Content displayed inside the glass card.
 */
@Composable
fun KosmosGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    backgroundColor: Color = Color.White,
    backgroundAlpha: Float = 0.08f,
    borderColor: Color = Color.White,
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit
) {
    val safeBackgroundAlpha = backgroundAlpha.coerceIn(0f, 1f)
    val safeBorderWidth = borderWidth.coerceAtLeast(0.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = backgroundColor.copy(alpha = safeBackgroundAlpha),
                shape = shape
            )
            .border(
                width = safeBorderWidth,
                color = borderColor.copy(alpha = 0.35f),
                shape = shape
            )
            .padding(contentPadding)
    ) {
        content()
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosGlassCard gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosGlassCardPreview() {
    PreviewGallery("KosmosGlassCard") {
        PreviewFrame("Glass on a gradient backdrop", "Translucent surface without background blur.", light = false) {
            Box(Modifier.background(Color(0xFF553399)).padding(16.dp)) {
                KosmosGlassCard { Column(Modifier.padding(20.dp)) { Text("Glass surface", color = Color.White); Text("Translucent card over purple", color = Color.White) } }
            }
        }

        PreviewFrame("Frosted cyan panel", "Stronger tint, custom border and softer corners.", light = false) {
            KosmosGlassCard(backgroundColor = Color(0xFF22D3EE), backgroundAlpha = 0.2f, borderColor = Color(0xFF22D3EE), borderWidth = 2.dp, shape = RoundedCornerShape(12.dp)) { Text("Tinted panel", Modifier.padding(24.dp)) }
        }

        PreviewFrame("Light glass panel", "Higher opacity over a colored backdrop.", light = true) {
            Box(Modifier.background(Color(0xFF2563EB)).padding(16.dp)) { KosmosGlassCard(backgroundAlpha = 0.65f) { Text("High opacity glass", Modifier.padding(24.dp), color = Color.Black) } }
        }

    }
}
