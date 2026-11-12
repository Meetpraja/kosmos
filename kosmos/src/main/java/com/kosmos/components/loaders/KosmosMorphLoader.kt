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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.rotate

/**
 * An animated loader that morphs between a circle, rounded square,
 * and an organic diamond-like shape.
 *
 * @param modifier Modifier applied to the loader.
 * @param color Main loader color.
 * @param size Size of the morphing shape.
 * @param strokeWidth Outline thickness.
 * @param animationDurationMillis Duration of one morph cycle.
 * @param isRunning Whether the animation should run.
 * @param filled Whether to draw a filled shape or an outline.
 */
@Composable
fun KosmosMorphLoader(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFB56BFF),
    size: Dp = 64.dp,
    strokeWidth: Dp = 4.dp,
    animationDurationMillis: Int = 1800,
    isRunning: Boolean = true,
    filled: Boolean = false
) {
    val progress = remember { Animatable(0f) }
    val duration = animationDurationMillis.coerceAtLeast(300)


    LaunchedEffect(isRunning, duration) {
        if (isRunning) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                    animation = tween(
                        durationMillis = duration,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
                )
            )
        } else {
            progress.snapTo(0f)
        }
    }


    val safeSize = size.coerceAtLeast(1.dp)
    val safeStroke = strokeWidth.coerceAtLeast(0.dp)

    Canvas(
        modifier = modifier.size(safeSize)
    ) {
        val shapeProgress = progress.value
        val center = Offset(this.size.width / 2f, this.size.height / 2f)

        val minDimension = this.size.minDimension
        val inset = safeStroke.toPx() / 2f
        val shapeSize = minDimension - inset * 2f

        if (shapeSize <= 0f) return@Canvas

        val half = shapeSize / 2f
        val cornerRadius = when {
            shapeProgress < 0.33f -> {
                val fraction = shapeProgress / 0.33f
                half * (1f - fraction)
            }

            shapeProgress < 0.66f -> {
                val fraction = (shapeProgress - 0.33f) / 0.33f
                half * fraction
            }

            else -> {
                val fraction = (shapeProgress - 0.66f) / 0.34f
                half * (1f - fraction)
            }
        }

        val rotation = shapeProgress * 180f

        val path = Path().apply {
            val points = listOf(
                Offset(center.x, center.y - half),
                Offset(center.x + half, center.y),
                Offset(center.x, center.y + half),
                Offset(center.x - half, center.y)
            )

            moveTo(points.first().x, points.first().y)

            points.drop(1).forEach { point ->
                lineTo(point.x, point.y)
            }

            close()
        }

        rotate(rotation, center) {
            if (filled) {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(center.x - half, center.y - half),
                    size = Size(shapeSize, shapeSize),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            } else {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(center.x - half, center.y - half),
                    size = Size(shapeSize, shapeSize),
                    cornerRadius = CornerRadius(cornerRadius),
                    style = Stroke(width = safeStroke.toPx())
                )
            }
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosMorphLoader gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosMorphLoaderPreview() {
    PreviewGallery("KosmosMorphLoader") {
        PreviewFrame("Outlined morph", "Interactive Preview reveals rotation and corner morphing.", light = false) {
            KosmosMorphLoader()
        }

        PreviewFrame("Filled cyan morph", "Filled shape with a faster cycle.", light = false) {
            KosmosMorphLoader(color = Color(0xFF22D3EE), filled = true, size = 90.dp, animationDurationMillis = 1000)
        }

        PreviewFrame("Stopped morph", "Static filled starting shape.", light = false) {
            KosmosMorphLoader(isRunning = false, filled = true, color = Color(0xFFFB7185))
        }

    }
}
