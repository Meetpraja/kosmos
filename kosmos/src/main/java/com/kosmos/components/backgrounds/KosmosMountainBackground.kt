
package com.kosmos.components.backgrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

/**
 * A decorative mountain landscape background featuring
 * layered mountains, a glowing moon and scattered stars.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param backgroundColor Base sky background color.
 * @param moonColor Main moon color.
 * @param moonGlowColor Color of the moon's outer glow.
 * @param starColor Color of the stars.
 * @param backMountainColor Color of the distant mountain.
 * @param middleMountainColor Color of the middle mountain.
 * @param frontMountainColor Color of the foreground mountain.
 * @param moonGlowAlpha Opacity of the moon glow.
 * @param skyGlowAlpha Opacity of the sky glow.
 * @param showMoon Controls moon visibility.
 * @param showStars Controls star visibility.
 */
@Composable
fun KosmosMountainBackground(
    modifier: Modifier = Modifier.fillMaxSize(),

    backgroundColor: Color = Color(0xFF050008),

    moonColor: Color = Color(0xFFE0E0E0),
    moonGlowColor: Color = Color.White,
    starColor: Color = Color.White,

    backMountainColor: Color = Color(0xFF0A000A),
    middleMountainColor: Color = Color(0xFF0B0611),
    frontMountainColor: Color = Color(0xFF050008),

    moonGlowAlpha: Float = 0.30f,
    skyGlowAlpha: Float = 0.30f,

    showMoon: Boolean = true,
    showStars: Boolean = true

) {

    Canvas(
        modifier = modifier
    ) {

        val w = size.width
        val h = size.height

        // Background
        drawRect(
            color = backgroundColor,
            size = size
        )

        // Sky Glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Black.copy(alpha = skyGlowAlpha),
                    Color.Black.copy(alpha = skyGlowAlpha * 0.5f),
                    Color.Transparent
                ),
                center = Offset(w * 0.5f, h * 0.82f),
                radius = w * 0.65f
            ),
            radius = w * 0.65f,
            center = Offset(w * 0.5f, h * 0.82f)
        )

        // Stars
        if (showStars) {

            val stars = listOf(
                Offset(0.12f, 0.12f),
                Offset(0.32f, 0.19f),
                Offset(0.72f, 0.10f),
                Offset(0.88f, 0.25f),
                Offset(0.20f, 0.38f),
                Offset(0.80f, 0.43f),
                Offset(0.52f, 0.28f),
                Offset(0.92f, 0.55f)
            )

            stars.forEachIndexed { index, point ->

                drawCircle(
                    color = starColor.copy(
                        alpha = if (index % 2 == 0) 0.8f else 0.4f
                    ),
                    radius = if (index % 3 == 0) 3.dp.toPx() else 1.8.dp.toPx(),
                    center = Offset(
                        point.x * w,
                        point.y * h
                    )
                )
            }
        }

        // Moon and Moon Glow
        if (showMoon) {

            val moonCenter = Offset(
                w * 0.78f,
                h * 0.72f
            )

            // Outer Moon Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        moonGlowColor.copy(alpha = moonGlowAlpha),
                        moonGlowColor.copy(alpha = moonGlowAlpha * 0.4f),
                        Color.Transparent
                    ),
                    center = moonCenter,
                    radius = w * 0.28f
                ),
                radius = w * 0.28f,
                center = moonCenter
            )

            // Moon
            drawCircle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        moonColor.copy(alpha = 0.5f),
                        moonColor.copy(alpha = 0.9f),
                        moonColor.copy(alpha = 0.7f)
                    ),
                    start = Offset(w * 0.65f, h * 0.62f),
                    end = Offset(w * 0.88f, h * 0.82f)
                ),
                radius = w * 0.105f,
                center = moonCenter
            )
        }

        // Distant Mountain
        val backMountain = Path().apply {

            moveTo(0f, h * 0.84f)
            lineTo(w * 0.17f, h * 0.79f)
            lineTo(w * 0.30f, h * 0.82f)
            lineTo(w * 0.45f, h * 0.76f)
            lineTo(w * 0.58f, h * 0.81f)
            lineTo(w * 0.76f, h * 0.77f)
            lineTo(w * 0.88f, h * 0.80f)
            lineTo(w, h * 0.76f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        drawPath(
            path = backMountain,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black,
                    backMountainColor,
                    backgroundColor
                ),
                startY = h * 0.76f,
                endY = h
            )
        )

        // Middle Mountain
        val middleMountain = Path().apply {

            moveTo(0f, h * 0.88f)
            lineTo(w * 0.20f, h * 0.84f)
            lineTo(w * 0.36f, h * 0.87f)
            lineTo(w * 0.53f, h * 0.80f)
            lineTo(w * 0.68f, h * 0.87f)
            lineTo(w * 0.84f, h * 0.83f)
            lineTo(w, h * 0.87f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        drawPath(
            path = middleMountain,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black,
                    middleMountainColor
                ),
                startY = h * 0.80f,
                endY = h
            )
        )

        // Foreground Mountain
        val frontMountain = Path().apply {

            moveTo(0f, h * 0.91f)
            lineTo(w * 0.18f, h * 0.88f)
            lineTo(w * 0.34f, h * 0.92f)
            lineTo(w * 0.51f, h * 0.85f)
            lineTo(w * 0.70f, h * 0.91f)
            lineTo(w * 0.86f, h * 0.87f)
            lineTo(w, h * 0.90f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        drawPath(
            path = frontMountain,
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black,
                    frontMountainColor
                ),
                startY = h * 0.85f,
                endY = h
            )
        )
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosMountainBackground gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosMountainBackgroundPreview() {
    PreviewGallery("KosmosMountainBackground") {
        PreviewFrame("Moonlit mountains", "Full landscape with moon and stars.", light = false) {
            KosmosMountainBackground(modifier = Modifier.fillMaxWidth().height(400.dp))
        }

        PreviewFrame("Purple night landscape", "Custom sky, mountains and moon glow.", light = false) {
            KosmosMountainBackground(modifier = Modifier.fillMaxWidth().height(400.dp), backgroundColor = Color(0xFF160B30), moonGlowColor = Color(0xFF8B5CF6), backMountainColor = Color(0xFF39215B), middleMountainColor = Color(0xFF251640))
        }

        PreviewFrame("Minimal mountain silhouette", "Hide moon and stars for a quiet background.", light = false) {
            KosmosMountainBackground(modifier = Modifier.fillMaxWidth().height(400.dp), showMoon = false, showStars = false, skyGlowAlpha = 0f)
        }

    }
}
