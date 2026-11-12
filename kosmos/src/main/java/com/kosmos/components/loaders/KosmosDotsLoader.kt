package com.kosmos.components.loaders

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

/**
 * A minimal animated dots loader with staggered vertical movement.
 *
 * @param modifier Modifier applied to the loader.
 * @param dotColor Color of the dots.
 * @param dotCount Number of animated dots.
 * @param dotSize Diameter of each dot.
 * @param dotSpacing Space between dots.
 * @param bounceHeight Maximum vertical movement of each dot.
 * @param animationDurationMillis Duration of one bounce cycle.
 * @param isRunning Whether the animation should run.
 */
@Composable
fun KosmosDotsLoader(
    modifier: Modifier = Modifier,
    dotColor: Color = Color(0xFFB56BFF),
    dotCount: Int = 3,
    dotSize: Dp = 10.dp,
    dotSpacing: Dp = 10.dp,
    bounceHeight: Dp = 10.dp,
    animationDurationMillis: Int = 600,
    isRunning: Boolean = true
) {
    val count = dotCount.coerceIn(1, 12)
    val duration = animationDurationMillis.coerceAtLeast(100)

    val animations = remember(count) {
        List(count) { Animatable(0f) }
    }

    LaunchedEffect(isRunning, count, duration) {
        if (!isRunning) {
            animations.forEach { it.snapTo(0f) }
            return@LaunchedEffect
        }

        while (isActive) {
            animations.forEachIndexed { index, animation ->
                launchDotAnimation(
                    animation = animation,
                    duration = duration,
                    delay = index * (duration / (count * 2))
                )
            }

            kotlinx.coroutines.delay(duration.toLong())
            animations.forEach { it.snapTo(0f) }
        }
    }

    val safeDotSize = dotSize.coerceAtLeast(0.dp)
    val safeSpacing = dotSpacing.coerceAtLeast(0.dp)
    val safeBounce = bounceHeight.coerceAtLeast(0.dp)

    Canvas(modifier = modifier) {
        val dotRadius = safeDotSize.toPx() / 2f
        val spacing = safeSpacing.toPx()
        val bounce = safeBounce.toPx()

        val totalWidth = count * dotRadius * 2f + (count - 1) * spacing
        val startX = (size.width - totalWidth) / 2f + dotRadius
        val centerY = size.height / 2f

        animations.forEachIndexed { index, animation ->
            val progress = animation.value
            val x = startX + index * (dotRadius * 2f + spacing)
            val y = centerY - bounce * progress

            drawCircle(
                color = dotColor,
                radius = dotRadius,
                center = Offset(x, y)
            )
        }
    }
}

private suspend fun launchDotAnimation(
    animation: Animatable<Float, *>,
    duration: Int,
    delay: Int
) {
    kotlinx.coroutines.delay(delay.toLong())

    animation.animateTo(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = duration / 2,
            easing = FastOutSlowInEasing
        )
    )

    animation.animateTo(
        targetValue = 0f,
        animationSpec = tween(
            durationMillis = duration / 2,
            easing = FastOutSlowInEasing
        )
    )
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosDotsLoader gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosDotsLoaderPreview() {
    PreviewGallery("KosmosDotsLoader") {
        PreviewFrame("Three bouncing dots", "Interactive Preview shows the bounce animation.", light = false) {
            KosmosDotsLoader(modifier = Modifier.size(180.dp, 70.dp))
        }

        PreviewFrame("Five compact dots", "Smaller cyan dots with a higher bounce.", light = false) {
            KosmosDotsLoader(modifier = Modifier.size(200.dp, 90.dp), dotColor = Color(0xFF22D3EE), dotCount = 5, dotSize = 8.dp, bounceHeight = 20.dp)
        }

        PreviewFrame("Stopped dots", "Static dots when isRunning is false.", light = false) {
            KosmosDotsLoader(modifier = Modifier.size(180.dp, 70.dp), isRunning = false, dotSize = 16.dp, dotSpacing = 14.dp)
        }

    }
}
