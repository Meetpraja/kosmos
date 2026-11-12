package com.kosmos.components.loaders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A rotating circular arc loader.
 *
 * @param modifier Modifier applied to the loader.
 * @param color Color of the animated arc.
 * @param trackColor Color of the background track.
 * @param strokeWidth Thickness of the circular stroke.
 * @param sweepAngle Length of the animated arc in degrees.
 * @param animationDurationMillis Duration of one rotation cycle.
 * @param isRunning Whether the animation should run.
 * @param showTrack Whether to show the background circle.
 */
@Composable
fun KosmosCircularProgressLoader(
    modifier: Modifier = Modifier.size(64.dp),
    color: Color = Color(0xFFB56BFF),
    trackColor: Color = Color.White.copy(alpha = 0.12f),
    strokeWidth: Dp = 4.dp,
    sweepAngle: Float = 100f,
    animationDurationMillis: Int = 1000,
    isRunning: Boolean = true,
    showTrack: Boolean = true
) {
    val rotation = if (isRunning) {
        val transition = rememberInfiniteTransition(label = "kosmosCircularLoader")

        val animatedRotation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDurationMillis.coerceAtLeast(100),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )
        animatedRotation
    } else {
        0f
    }

    Canvas(modifier = modifier) {
        val stroke = strokeWidth.coerceAtLeast(0.dp).toPx()
        val radius = size.minDimension / 2f - stroke / 2f

        if (radius <= 0f || stroke <= 0f) return@Canvas

        val topLeft = Offset(
            x = (size.width - radius * 2f) / 2f,
            y = (size.height - radius * 2f) / 2f
        )

        val arcSize = androidx.compose.ui.geometry.Size(
            width = radius * 2f,
            height = radius * 2f
        )

        if (showTrack) {
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = stroke
                )
            )
        }

        drawArc(
            color = color,
            startAngle = rotation,
            sweepAngle = sweepAngle.coerceIn(1f, 359f),
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosCircularProgressLoader gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosCircularProgressLoaderPreview() {
    PreviewGallery("KosmosCircularProgressLoader") {
        PreviewFrame("Tracked circular loader", "Interactive Preview shows rotation.", light = false) {
            KosmosCircularProgressLoader()
        }

        PreviewFrame("Compact untracked arc", "Short arc, thin stroke, cyan accent.", light = false) {
            KosmosCircularProgressLoader(modifier = Modifier.size(40.dp), color = Color(0xFF22D3EE), showTrack = false, strokeWidth = 2.dp, sweepAngle = 60f)
        }

        PreviewFrame("Bold long arc", "Large ring with a longer sweep and slower rotation.", light = false) {
            KosmosCircularProgressLoader(modifier = Modifier.size(100.dp), strokeWidth = 8.dp, sweepAngle = 260f, animationDurationMillis = 2200)
        }

    }
}
