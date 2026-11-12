package com.kosmos.components.kosmoscomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * A customizable gradient button for Jetpack Compose.
 *
 * Supports custom gradient colors, shapes, borders,
 * content slots and enabled/disabled states.
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier used to control size and positioning.
 * @param enabled Whether the button accepts click interactions.
 * @param gradientColors Colors used to create the gradient.
 * @param shape Shape of the button.
 * @param borderColor Color of the button border.
 * @param borderWidth Thickness of the button border.
 * @param contentPadding Padding around the button content.
 * @param content Content displayed inside the button.
 */
@Composable
fun KosmosGradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    gradientColors: List<Color> = listOf(
        Color(0xFF8B5CF6),
        Color(0xFF6366F1)
    ),
    shape: Shape = RoundedCornerShape(16.dp),
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 24.dp,
        vertical = 14.dp
    ),
    content: @Composable RowScope.() -> Unit
) {
    val safeColors = gradientColors.ifEmpty {
        listOf(Color.Gray, Color.DarkGray)
    }

    val safeBorderWidth = borderWidth.coerceAtLeast(0.dp)

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val gradientBrush = remember(safeColors) {
        Brush.horizontalGradient(safeColors)
    }

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .clip(shape)
            .background(
                brush = gradientBrush,
                shape = shape
            )
            .border(
                width = safeBorderWidth,
                color = borderColor,
                shape = shape
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                onClick = onClick
            )
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content
    )
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosGradientButton gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosGradientButtonPreview() {
    PreviewGallery("KosmosGradientButton") {
        PreviewFrame("Primary gradient button", "Rounded button with a text content slot.", light = false) {
            KosmosGradientButton(onClick = {}) { Text("Get started", color = Color.White) }
        }

        PreviewFrame("Pill icon button", "Custom colors, pill shape and icon-like content.", light = false) {
            KosmosGradientButton(onClick = {}, gradientColors = listOf(Color(0xFF22D3EE), Color(0xFF2563EB)), shape = CircleShape) { Text("+  Create project", color = Color.White) }
        }

        PreviewFrame("Disabled bordered button", "Disabled click behavior with custom border and padding.", light = false) {
            KosmosGradientButton(onClick = {}, enabled = false, borderColor = Color.White, borderWidth = 1.dp, contentPadding = PaddingValues(20.dp)) { Text("Unavailable", color = Color.White) }
        }

    }
}
