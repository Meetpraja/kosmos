package com.kosmos.components.loaders

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.sin


/**
 * An animated wave loader featuring vertically moving rounded bars.
 *
 * The wave animation repeats infinitely while [isRunning] is true.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param waveColor Color of the wave bars.
 * @param barCount Number of bars displayed (1 to 30).
 * @param barWidth Width of each bar.
 * @param barSpacing Spacing between adjacent bars.
 * @param minBarHeight Minimum height of each bar.
 * @param maxBarHeight Maximum height of each bar.
 * @param cornerRadius Corner radius of the bars.
 * @param animationDurationMillis Duration of one complete wave cycle.
 * @param isRunning Whether the animation is running.
 */
@Composable
fun KosmosWaveLoader(
    modifier: Modifier = Modifier.size(
        width = 120.dp,
        height = 60.dp
    ),

    // Colors
    waveColor: Color = Color(0xFFB56BFF),

    // Appearance
    barCount: Int = 5,
    barWidth: Dp = 5.dp,
    barSpacing: Dp = 6.dp,
    minBarHeight: Dp = 12.dp,
    maxBarHeight: Dp = 48.dp,
    cornerRadius: Dp = 4.dp,

    // Animation
    animationDurationMillis: Int = 900,
    isRunning: Boolean = true
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

    val safeBarCount = barCount.coerceIn(1, 30)
    val safeBarWidth = barWidth.coerceAtLeast(0.dp)
    val safeBarSpacing = barSpacing.coerceAtLeast(0.dp)
    val safeMinHeight = minBarHeight.coerceAtLeast(0.dp)
    val safeMaxHeight = maxBarHeight.coerceAtLeast(safeMinHeight)
    val safeCornerRadius = cornerRadius.coerceAtLeast(0.dp)

    Canvas(modifier = modifier) {

        val width = safeBarWidth.toPx()
        val spacing = safeBarSpacing.toPx()

        val totalWidth =
            safeBarCount * width +
                    (safeBarCount - 1) * spacing

        val startX = (size.width - totalWidth) / 2f
        val centerY = size.height / 2f

        val minimumHeight = safeMinHeight.toPx()
            .coerceAtMost(size.height)

        val maximumHeight = safeMaxHeight.toPx()
            .coerceAtMost(size.height)
            .coerceAtLeast(minimumHeight)

        val radius = safeCornerRadius.toPx()
            .coerceAtMost(width / 2f)

        val phase = progress.value * 2f * PI.toFloat()

        repeat(safeBarCount) { index ->

            val waveOffset = index * 0.75f

            val waveValue = (
                    (sin(phase - waveOffset) + 1f) / 2f
                    ).toFloat()

            val barHeight = minimumHeight +
                    (maximumHeight - minimumHeight) * waveValue

            val top = centerY - barHeight / 2f

            val left = startX + index * (width + spacing)

            drawRoundRect(
                color = waveColor,
                topLeft = Offset(left, top),
                size = androidx.compose.ui.geometry.Size(
                    width = width,
                    height = barHeight
                ),
                cornerRadius = CornerRadius(
                    x = radius,
                    y = radius
                )
            )
        }
    }
}