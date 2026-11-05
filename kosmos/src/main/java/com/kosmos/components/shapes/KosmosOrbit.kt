
package com.kosmos.components.shapes

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws customizable elliptical orbital rings around a central circle.
 *
 * Supports different orbital styles, glow effects, orbiting objects,
 * custom dimensions, tilt, rotation and optional animation.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param orbitColor Color of the orbital rings.
 * @param centerColor Fill color of the central circle.
 * @param orbitCount Number of orbital rings.
 * @param strokeWidth Thickness of the orbital rings.
 * @param centerRadius Radius of the central circle.
 * @param orbitSpacing Distance between orbital rings.
 * @param orbitTilt Vertical compression of the orbital rings.
 * @param rotation Rotation angle of the entire orbital design.
 * @param orbitAlpha Transparency of the orbital rings.
 * @param orbitStyle Visual style of the orbital rings.
 * @param showCenter Whether to draw the central circle.
 * @param centerBorderColor Border color of the central circle.
 * @param centerBorderWidth Width of the central circle border.
 * @param showOrbitingObjects Whether to draw orbiting objects.
 * @param orbitingObjectColor Color of the orbiting objects.
 * @param orbitingObjectRadius Radius of the orbiting objects.
 * @param glowColor Color used for the glow effect.
 * @param glowRadius Radius of the glow effect.
 * @param animated Whether to animate the orbital design.
 * @param rotationDuration Duration of one complete animation cycle.
 * @param reverseRotation Whether to reverse the animation direction.
 */
@Composable
fun KosmosOrbit(
    modifier: Modifier = Modifier.size(220.dp),

    // Colors
    orbitColor: Color = Color(0xFF8B5CF6),
    centerColor: Color = Color.White,
    glowColor: Color = Color.Transparent,

    // Dimensions
    centerRadius: Dp = 9.dp,
    strokeWidth: Dp = 2.dp,
    orbitSpacing: Dp = 12.dp,
    centerBorderWidth: Dp = 0.dp,
    glowRadius: Dp = 0.dp,
    orbitingObjectRadius: Dp = 4.dp,

    // Orbit configuration
    orbitCount: Int = 3,
    orbitTilt: Float = 0.55f,
    rotation: Float = 0f,
    orbitAlpha: Float = 0.8f,
    orbitStyle: OrbitStyle = OrbitStyle.SOLID,

    // Center configuration
    showCenter: Boolean = true,
    centerBorderColor: Color = Color.Transparent,

    // Orbiting objects
    showOrbitingObjects: Boolean = false,
    orbitingObjectColor: Color = Color.White,

    // Animation
    animated: Boolean = false,
    rotationDuration: Int = 8000,
    reverseRotation: Boolean = false
) {
    val animation = rememberInfiniteTransition(label = "kosmos_orbit")

    val animatedRotation by animation.animateFloat(
        initialValue = 0f,
        targetValue = if (reverseRotation) -360f else 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = rotationDuration.coerceAtLeast(1),
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_rotation"
    )

    val currentRotation = rotation + if (animated) animatedRotation else 0f

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)

        val count = orbitCount.coerceIn(1, 8)
        val safeTilt = orbitTilt.coerceIn(0.1f, 1f)
        val safeAlpha = orbitAlpha.coerceIn(0f, 1f)

        val stroke = strokeWidth.toPx().coerceAtLeast(0f)
        val centerSize = centerRadius.toPx().coerceAtLeast(0f)
        val spacing = orbitSpacing.toPx().coerceAtLeast(0f)
        val objectRadius = orbitingObjectRadius.toPx().coerceAtLeast(0f)
        val glowSize = glowRadius.toPx().coerceAtLeast(0f)

        val availableRadius = minOf(size.width, size.height) * 0.42f

        // Calculate orbital radii while keeping them inside the canvas.
        val baseRadius = (availableRadius - spacing * (count - 1) / 2f)
            .coerceAtLeast(0f)

        repeat(count) { index ->
            val radius = (baseRadius - spacing * (count - index - 1))
                .coerceAtLeast(0f)

            val ovalSize = Size(
                width = radius * 2f,
                height = radius * 2f * safeTilt
            )

            val topLeft = Offset(
                x = center.x - radius,
                y = center.y - radius * safeTilt
            )

            val alpha = safeAlpha * (0.45f + 0.55f * (index + 1) / count)

            rotate(degrees = currentRotation, pivot = center) {
                // Glow layer
                if (glowSize > 0f && glowColor.alpha > 0f) {
                    drawOval(
                        color = glowColor.copy(alpha = glowColor.alpha * alpha * 0.25f),
                        topLeft = topLeft,
                        size = ovalSize,
                        style = Stroke(
                            width = glowSize,
                            pathEffect = getOrbitPathEffect(OrbitStyle.SOLID, stroke)
                        )
                    )
                }

                // Main orbital ring
                drawOval(
                    color = orbitColor.copy(alpha = orbitColor.alpha * alpha),
                    topLeft = topLeft,
                    size = ovalSize,
                    style = Stroke(
                        width = stroke,
                        pathEffect = getOrbitPathEffect(orbitStyle, stroke)
                    )
                )

                // Orbiting object
                if (showOrbitingObjects && objectRadius > 0f && radius > 0f) {
                    val angle = if (animated) {
                        animatedRotation * PI.toFloat() / 180f
                    } else {
                        0f
                    }

                    val objectPosition = Offset(
                        x = center.x + cos(angle + index * 1.2f) * radius,
                        y = center.y + sin(angle + index * 1.2f) * radius * safeTilt
                    )

                    drawCircle(
                        color = orbitingObjectColor,
                        radius = objectRadius,
                        center = objectPosition
                    )
                }
            }
        }

        // Central circle
        if (showCenter && centerSize > 0f) {
            drawCircle(
                color = centerColor,
                radius = centerSize,
                center = center
            )

            val borderWidth = centerBorderWidth.toPx().coerceAtLeast(0f)

            if (borderWidth > 0f) {
                drawCircle(
                    color = centerBorderColor,
                    radius = centerSize,
                    center = center,
                    style = Stroke(width = borderWidth)
                )
            }
        }
    }
}

/**
 * Creates the appropriate path effect for the requested orbital style.
 */
private fun getOrbitPathEffect(
    style: OrbitStyle,
    strokeWidth: Float
): PathEffect? {
    return when (style) {
        OrbitStyle.SOLID -> null

        OrbitStyle.DASHED -> PathEffect.dashPathEffect(
            floatArrayOf(strokeWidth * 5f, strokeWidth * 3f)
        )

        OrbitStyle.DOTTED -> PathEffect.dashPathEffect(
            floatArrayOf(strokeWidth, strokeWidth * 2.5f)
        )
    }
}
