
package com.kosmos.components.backgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A circular gradient background with a subtle gradient border.
 *
 * @param modifier Modifier used to control size, position and layout.
 * @param highlightColor Primary gradient highlight color.
 * @param backgroundColor Base gradient background color.
 * @param borderColor Gradient border color.
 * @param borderWidth Width of the circular border.
 */
@Composable
fun KosmosRoundBackground(
    modifier: Modifier = Modifier.size(350.dp),

    highlightColor: Color = Color(0xFF141419),
    backgroundColor: Color = Color(0xFF0A0A0A),

    borderColor: Color = Color.Gray.copy(alpha = 0.4f),
    borderWidth: Dp = 2.dp

) {

    Canvas(
        modifier = modifier
    ) {

        val radius = size.minDimension / 2
        val strokeWidth = borderWidth.toPx().coerceAtLeast(0f)

        // Radial background gradient
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    highlightColor,
                    backgroundColor
                ),
                center = Offset(
                    size.width * 0.30f,
                    size.height * 0.35f
                ),
                radius = radius * 1.7f
            ),
            radius = radius
        )

        // Gradient circular border
        if (strokeWidth > 0f) {
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        borderColor,
                        highlightColor.copy(alpha = 0.2f)
                    )
                ),
                radius = (radius - strokeWidth / 2).coerceAtLeast(0f),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosRoundBackground gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosRoundBackgroundPreview() {
    PreviewGallery("KosmosRoundBackground") {
        PreviewFrame("Dark round surface", "Default radial shading and subtle border.", light = false) {
            KosmosRoundBackground(modifier = Modifier.size(300.dp))
        }

        PreviewFrame("Purple glow surface", "Custom highlight and a stronger circular border.", light = false) {
            KosmosRoundBackground(modifier = Modifier.size(300.dp), highlightColor = Color(0xFF8B5CF6), borderColor = Color(0xFF22D3EE), borderWidth = 4.dp)
        }

        PreviewFrame("Light round surface", "A pale surface without a border.", light = true) {
            KosmosRoundBackground(modifier = Modifier.size(300.dp), highlightColor = Color.White, backgroundColor = Color(0xFFE0E7FF), borderWidth = 0.dp)
        }

    }
}
