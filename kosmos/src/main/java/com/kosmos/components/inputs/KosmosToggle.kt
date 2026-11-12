
package com.kosmos.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.selection.toggleable

/**
 * A soft neomorphic toggle switch with a recessed track
 * and a raised animated thumb.
 */
@Composable
fun KosmosToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,

    surfaceColor: Color = Color(0xFF17171D),
    activeThumbColor: Color = Color(0xFFB56BFF),
    inactiveThumbColor: Color = Color(0xFFEEEEF2),

    lightShadowColor: Color = Color(0xFF353540),
    darkShadowColor: Color = Color(0xFF09090D),

    disabledThumbColor: Color = Color(0xFF777780),

    trackWidth: Dp = 58.dp,
    trackHeight: Dp = 34.dp,
    thumbSize: Dp = 25.dp,

    animationEnabled: Boolean = true
) {
    val progress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = if (animationEnabled) {
            spring(
                dampingRatio = 0.78f,
                stiffness = Spring.StiffnessMediumLow
            )
        } else {
            spring(stiffness = Spring.StiffnessHigh)
        },
        label = "KosmosNeumorphicToggle"
    )

    val safeWidth = trackWidth.coerceAtLeast(1.dp)
    val safeHeight = trackHeight.coerceAtLeast(1.dp)
    val safeThumb = thumbSize.coerceAtLeast(1.dp)

    Canvas(
        modifier = modifier
            .size(safeWidth, safeHeight)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onValueChange = onCheckedChange
            )
    ) {
        val width = size.width
        val height = size.height

        val padding = height * 0.13f
        val radius = height / 2f

        val thumbRadius = minOf(
            safeThumb.toPx() / 2f,
            radius - padding
        )

        val travel = (
                width - padding * 2f - thumbRadius * 2f
                ).coerceAtLeast(0f)

        val centerX = padding + thumbRadius + travel * progress
        val centerY = height / 2f

        val shadowOffset = 2.dp.toPx()
        val trackCorner = CornerRadius(radius)

        val trackAlpha = if (enabled) 1f else 0.55f

        // Recessed track shadow
        drawRoundRect(
            color = darkShadowColor.copy(alpha = 0.75f * trackAlpha),
            topLeft = Offset(
                shadowOffset,
                shadowOffset
            ),
            size = size,
            cornerRadius = trackCorner
        )

        // Track surface
        drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    surfaceColor.copy(alpha = trackAlpha),
                    surfaceColor.copy(alpha = trackAlpha)
                )
            ),
            cornerRadius = trackCorner
        )

        // Subtle recessed highlight
        drawRoundRect(
            color = lightShadowColor.copy(alpha = 0.25f * trackAlpha),
            cornerRadius = trackCorner,
            style = Stroke(width = 1.dp.toPx())
        )

        // Thumb lower-right shadow
        drawCircle(
            color = darkShadowColor.copy(
                alpha = if (enabled) 0.9f else 0.35f
            ),
            radius = thumbRadius,
            center = Offset(
                centerX + shadowOffset,
                centerY + shadowOffset
            )
        )

        // Thumb upper-left soft highlight
        drawCircle(
            color = lightShadowColor.copy(
                alpha = if (enabled) 0.9f else 0.25f
            ),
            radius = thumbRadius,
            center = Offset(
                centerX - shadowOffset * 0.65f,
                centerY - shadowOffset * 0.65f
            )
        )

        // Raised thumb
        val thumbColor = when {
            !enabled -> disabledThumbColor
            checked -> activeThumbColor
            else -> inactiveThumbColor
        }

        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(
                    thumbColor.copy(alpha = 1f),
                    thumbColor.copy(alpha = 0.88f)
                ),
                start = Offset(
                    centerX - thumbRadius,
                    centerY - thumbRadius
                ),
                end = Offset(
                    centerX + thumbRadius,
                    centerY + thumbRadius
                )
            ),
            radius = thumbRadius,
            center = Offset(centerX, centerY)
        )

        // Very subtle thumb edge
        drawCircle(
            color = Color.White.copy(
                alpha = if (enabled) 0.16f else 0.06f
            ),
            radius = thumbRadius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 0.7.dp.toPx())
        )
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosToggle gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosTogglePreview() {
    PreviewGallery("KosmosToggle") {
        PreviewFrame("Interactive toggle", "Tap in Interactive Preview to change state.", light = false) {
            var checked by remember { mutableStateOf(false) }
            KosmosToggle(checked = checked, onCheckedChange = { checked = it })
        }

        PreviewFrame("Active cyan toggle", "Custom active thumb color and larger dimensions.", light = false) {
            var checked by remember { mutableStateOf(true) }
            KosmosToggle(checked = checked, onCheckedChange = { checked = it }, activeThumbColor = Color(0xFF22D3EE), trackWidth = 80.dp, trackHeight = 44.dp, thumbSize = 32.dp)
        }

        PreviewFrame("Disabled toggle states", "Compare disabled on and off switches.", light = false) {
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) { KosmosToggle(checked = false, onCheckedChange = {}, enabled = false); KosmosToggle(checked = true, onCheckedChange = {}, enabled = false) }
        }

    }
}
