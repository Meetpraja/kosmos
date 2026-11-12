package com.kosmos.components.shapes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws an organic blob shape.
 *
 * @param modifier Modifier used to control size and positioning.
 * @param color Fill color of the blob.
 * @param pointCount Number of points used to construct the shape.
 * @param irregularity Controls how much the shape deviates from a circle.
 */
@Composable
fun KosmosBlob(
    modifier: Modifier = Modifier.size(200.dp),
    color: Color = Color(0xFF8B5CF6),
    pointCount: Int = 8,
    irregularity: Float = 0.22f
) {
    Canvas(modifier = modifier) {
        val points = pointCount.coerceAtLeast(4)
        val safeIrregularity = irregularity.coerceIn(0f, 0.45f)

        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = minOf(size.width, size.height) / 2f

        val coordinates = List(points) { index ->
            val angle = (2.0 * Math.PI * index / points).toFloat()
            val variation = if (index % 2 == 0) {
                1f + safeIrregularity
            } else {
                1f - safeIrregularity
            }

            Offset(
                x = center.x + cos(angle) * radius * variation,
                y = center.y + sin(angle) * radius * variation
            )
        }

        val path = Path().apply {
            moveTo(
                (coordinates[0].x + coordinates.last().x) / 2f,
                (coordinates[0].y + coordinates.last().y) / 2f
            )

            coordinates.forEachIndexed { index, point ->
                val next = coordinates[(index + 1) % points]
                val midpoint = Offset(
                    (point.x + next.x) / 2f,
                    (point.y + next.y) / 2f
                )
                quadraticTo(point.x, point.y, midpoint.x, midpoint.y)
            }

            close()
        }

        drawPath(path = path, color = color)
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosBlob gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosBlobPreview() {
    PreviewGallery("KosmosBlob") {
        PreviewFrame("Soft organic blob", "Default purple fill with eight control points.", light = false) {
            KosmosBlob()
        }

        PreviewFrame("Rounded cyan blob", "More points and low irregularity produce a softer outline.", light = false) {
            KosmosBlob(color = Color(0xFF22D3EE), pointCount = 12, irregularity = 0.08f)
        }

        PreviewFrame("Angular coral blob", "Fewer points and deeper irregularity change the silhouette.", light = false) {
            KosmosBlob(color = Color(0xFFFB7185), pointCount = 5, irregularity = 0.42f)
        }

    }
}
