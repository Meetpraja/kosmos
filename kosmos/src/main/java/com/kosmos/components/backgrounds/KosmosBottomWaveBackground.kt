
package com.kosmos.components.backgrounds

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A decorative bottom wave background with three layered
 * curved waves and subtle glowing stroke effects.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param firstWaveColor Color of the first wave.
 * @param secondWaveColor Color of the second wave.
 * @param thirdWaveColor Color of the third wave.
 * @param glowAlpha Opacity of the outer glow.
 * @param waveAlpha Opacity of the middle wave layer.
 * @param highlightAlpha Opacity of the inner highlight.
 * @param glowWidth Width of the outer glow stroke.
 * @param waveWidth Width of the middle wave stroke.
 * @param highlightWidth Width of the inner highlight stroke.
 */
@Composable
fun KosmosBottomWaveBackground(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(280.dp),

    firstWaveColor: Color = Color(0xFF323233),
    secondWaveColor: Color = Color(0xFF85858F),
    thirdWaveColor: Color = Color(0xFF161617),

    glowAlpha: Float = 0.05f,
    waveAlpha: Float = 0.22f,
    highlightAlpha: Float = 0.9f,

    glowWidth: Dp = 20.dp,
    waveWidth: Dp = 7.dp,
    highlightWidth: Dp = 1.5.dp

) {

    Canvas(
        modifier = modifier
    ) {

        val w = size.width
        val h = size.height

        val glowStroke = glowWidth.toPx().coerceAtLeast(0f)
        val waveStroke = waveWidth.toPx().coerceAtLeast(0f)
        val highlightStroke = highlightWidth.toPx().coerceAtLeast(0f)

        // First Wave
        val wave1 = Path().apply {

            moveTo(-w * 0.05f, h * 0.38f)

            cubicTo(
                w * 0.18f, h * 0.62f,
                w * 0.32f, h * 0.78f,
                w * 0.52f, h * 0.58f
            )

            cubicTo(
                w * 0.72f, h * 0.36f,
                w * 0.88f, h * 0.48f,
                w * 1.05f, h * 0.42f
            )
        }

        // Second Wave
        val wave2 = Path().apply {

            moveTo(-w * 0.05f, h * 0.52f)

            cubicTo(
                w * 0.16f, h * 0.82f,
                w * 0.32f, h * 0.90f,
                w * 0.53f, h * 0.65f
            )

            cubicTo(
                w * 0.72f, h * 0.43f,
                w * 0.88f, h * 0.62f,
                w * 1.05f, h * 0.50f
            )
        }

        // Third Wave
        val wave3 = Path().apply {

            moveTo(-w * 0.05f, h * 0.64f)

            cubicTo(
                w * 0.18f, h * 0.88f,
                w * 0.34f, h * 0.52f,
                w * 0.55f, h * 0.46f
            )

            cubicTo(
                w * 0.75f, h * 0.38f,
                w * 0.88f, h * 0.78f,
                w * 1.05f, h * 0.65f
            )
        }

        // Draw all three wave layers
        val waves = listOf(
            wave1 to firstWaveColor,
            wave2 to secondWaveColor,
            wave3 to thirdWaveColor
        )

        waves.forEach { (path, color) ->

            // Outer Glow
            if (glowStroke > 0f) {
                drawPath(
                    path = path,
                    color = color.copy(alpha = glowAlpha),
                    style = Stroke(
                        width = glowStroke,
                        cap = StrokeCap.Round
                    )
                )
            }

            // Middle Wave Layer
            if (waveStroke > 0f) {
                drawPath(
                    path = path,
                    color = color.copy(alpha = waveAlpha),
                    style = Stroke(
                        width = waveStroke,
                        cap = StrokeCap.Round
                    )
                )
            }

            // Inner Highlight
            if (highlightStroke > 0f) {
                drawPath(
                    path = path,
                    color = color.copy(alpha = highlightAlpha),
                    style = Stroke(
                        width = highlightStroke,
                        cap = StrokeCap.Round
                    )
                )
            }
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosBottomWaveBackground gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosBottomWaveBackgroundPreview() {
    PreviewGallery("KosmosBottomWaveBackground") {
        PreviewFrame("Subtle layered waves", "Default neutral strokes.", light = false) {
            KosmosBottomWaveBackground()
        }

        PreviewFrame("Neon layered waves", "Colored strokes and wider glow.", light = false) {
            KosmosBottomWaveBackground(firstWaveColor = Color(0xFF8B5CF6), secondWaveColor = Color(0xFF22D3EE), thirdWaveColor = Color(0xFFEC4899), glowAlpha = 0.3f, waveAlpha = 0.7f, glowWidth = 30.dp)
        }

        PreviewFrame("Fine wave divider", "Compact height and thin highlight strokes.", light = false) {
            KosmosBottomWaveBackground(modifier = Modifier.fillMaxWidth().height(120.dp), glowWidth = 0.dp, waveWidth = 2.dp, highlightWidth = 1.dp)
        }

    }
}
