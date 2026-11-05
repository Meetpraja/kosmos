package com.kosmos.components.loaders

import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * An animated orbital loader with rotating arcs and orbiting dots.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param orbitColor Color of the orbital arcs and dots.
 * @param trackColor Color of the background orbital tracks.
 * @param orbitCount Number of orbital rings (1 to 5).
 * @param strokeWidth Thickness of the orbital rings.
 * @param dotRadius Radius of the orbiting dots.
 * @param animationDurationMillis Duration of one complete rotation.
 * @param showTrack Whether to display background orbital tracks.
 */
@Composable
fun KosmosOrbitLoader(
    modifier: Modifier = Modifier.size(80.dp),

    // Colors
    orbitColor: Color = Color(0xFFB56BFF),
    trackColor: Color = Color.White.copy(alpha = 0.12f),

    // Appearance
    orbitCount: Int = 3,
    strokeWidth: Dp = 2.dp,
    dotRadius: Dp = 3.dp,

    // Animation
    animationDurationMillis: Int = 1800,
    isRunning: Boolean = true,

    // Options
    showTrack: Boolean = true
) {
    val transition = rememberInfiniteTransition(
        label = "KosmosOrbitTransition"
    )

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = if (isRunning) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDurationMillis.coerceAtLeast(100),
                easing = LinearEasing
            )
        ),
        label = "KosmosOrbitRotation"
    )

    val safeOrbitCount = orbitCount.coerceIn(1, 5)
    val safeStrokeWidth = strokeWidth.coerceAtLeast(0.dp)
    val safeDotRadius = dotRadius.coerceAtLeast(0.dp)

    Canvas(modifier = modifier) {

        val center = Offset(
            x = size.width / 2f,
            y = size.height / 2f
        )

        val maxRadius = size.minDimension * 0.38f
        val stroke = safeStrokeWidth.toPx()
        val dot = safeDotRadius.toPx()

        repeat(safeOrbitCount) { index ->

            val fraction = (index + 1f) / safeOrbitCount

            val radius = maxRadius * (
                    0.55f + fraction * 0.45f
                    )

            val arcSize = Size(
                width = radius * 2f,
                height = radius * 2f
            )

            val arcTopLeft = Offset(
                x = center.x - radius,
                y = center.y - radius
            )

            rotate(
                degrees = rotation + (index * 120f),
                pivot = center
            ) {

                // Background orbital track
                if (showTrack) {
                    drawCircle(
                        color = trackColor,
                        radius = radius,
                        center = center,
                        style = Stroke(width = stroke)
                    )
                }

                // Rotating orbital arc
                drawArc(
                    color = orbitColor.copy(
                        alpha = 0.65f + (fraction * 0.35f)
                    ),
                    startAngle = -130f,
                    sweepAngle = 235f,
                    useCenter = false,
                    topLeft = arcTopLeft,
                    size = arcSize,
                    style = Stroke(width = stroke)
                )

                // Orbiting dot
                drawCircle(
                    color = orbitColor,
                    radius = dot,
                    center = Offset(
                        x = center.x,
                        y = center.y - radius
                    )
                )
            }
        }
    }
}