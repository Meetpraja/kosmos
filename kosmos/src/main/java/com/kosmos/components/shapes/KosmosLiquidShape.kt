package com.kosmos.components.shapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

/**
 * Draws a smooth liquid-style curved shape.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param color Fill color of the shape.
 * @param curveHeight Controls the vertical position of the curve.
 * @param curveDepth Controls the depth of the liquid curve.
 */
@Composable
fun KosmosLiquidShape(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(180.dp),
    color: Color = Color(0xFF7C3AED),
    curveHeight: Float = 0.45f,
    curveDepth: Float = 0.25f
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val safeCurveHeight = curveHeight.coerceIn(0f, 1f)
        val safeCurveDepth = curveDepth.coerceIn(0f, 0.5f)

        val baseY = height * safeCurveHeight
        val depth = height * safeCurveDepth

        val path = Path().apply {
            moveTo(0f, baseY)

            cubicTo(
                width * 0.25f, baseY - depth,
                width * 0.35f, baseY + depth,
                width * 0.55f, baseY
            )

            cubicTo(
                width * 0.75f, baseY - depth,
                width * 0.85f, baseY + depth,
                width, baseY - depth * 0.2f
            )

            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(path = path, color = color)
    }
}
