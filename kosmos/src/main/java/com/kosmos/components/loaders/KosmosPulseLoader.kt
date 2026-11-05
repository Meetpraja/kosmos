package com.kosmos.components.loaders

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

/**
 * An animated pulse loader with expanding and fading circular rings.
 *
 * The animation repeats infinitely while [isRunning] is true.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param pulseColor Color of the pulse rings.
 * @param centerColor Color of the central dot.
 * @param pulseCount Number of expanding rings (1 to 5).
 * @param strokeWidth Thickness of the pulse rings.
 * @param centerRadius Radius of the central dot.
 * @param maxRadius Maximum radius reached by each pulse.
 * @param animationDurationMillis Duration of one pulse cycle.
 * @param isRunning Whether the animation is running.
 * @param showCenterDot Whether to display the central dot.
 */
@Composable
fun KosmosPulseLoader(
    modifier: Modifier = Modifier.size(80.dp),

    // Colors
    pulseColor: Color = Color(0xFFB56BFF),
    centerColor: Color = Color.White,

    // Appearance
    pulseCount: Int = 3,
    strokeWidth: Dp = 2.dp,
    centerRadius: Dp = 5.dp,
    maxRadius: Dp = 34.dp,

    // Animation
    animationDurationMillis: Int = 1200,
    isRunning: Boolean = true,

    // Options
    showCenterDot: Boolean = true
) {
    val progress = remember {
        Animatable(0f)
    }

    val duration = animationDurationMillis.coerceAtLeast(100)

    LaunchedEffect(isRunning, duration) {
        if (isRunning) {
            progress.snapTo(0f)

            while (isActive) {
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = duration
                    )
                )

                progress.snapTo(0f)
            }
        } else {
            progress.snapTo(0f)
        }
    }

    val safePulseCount = pulseCount.coerceIn(1, 5)
    val safeStrokeWidth = strokeWidth.coerceAtLeast(0.dp)
    val safeCenterRadius = centerRadius.coerceAtLeast(0.dp)
    val safeMaxRadius = maxRadius.coerceAtLeast(0.dp)

    Canvas(modifier = modifier) {

        val center = Offset(
            x = size.width / 2f,
            y = size.height / 2f
        )

        val stroke = safeStrokeWidth.toPx()
        val centerDot = safeCenterRadius.toPx()
        val maximumRadius = minOf(
            safeMaxRadius.toPx(),
            size.minDimension / 2f
        )

        val currentProgress = progress.value

        if (isRunning) {
            repeat(safePulseCount) { index ->

                val stagger = index.toFloat() / safePulseCount

                val phase = (
                        currentProgress + stagger
                        ) % 1f

                val radius = centerDot +
                        (maximumRadius - centerDot) * phase

                val alpha = (1f - phase).coerceIn(0f, 1f)

                drawCircle(
                    color = pulseColor.copy(alpha = alpha),
                    radius = radius,
                    center = center,
                    style = Stroke(width = stroke)
                )
            }
        }

        if (showCenterDot) {
            drawCircle(
                color = centerColor,
                radius = centerDot,
                center = center
            )
        }
    }
}

