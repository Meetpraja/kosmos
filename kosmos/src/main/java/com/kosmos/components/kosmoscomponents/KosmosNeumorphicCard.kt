
package com.kosmos.components.kosmoscomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.ui.geometry.Offset

/**
 * Visual styles available for KosmosNeumorphicCard.
 */
enum class KosmosNeumorphicStyle {
    Raised,
    Flat,
    Pressed
}

/**
 * A customizable neumorphic card with directional outer
 * and inner shadows.
 *
 * @param modifier Modifier used for sizing and positioning.
 * @param shape Shape of the card.
 * @param style Visual appearance of the card.
 * @param surfaceColor Card background color.
 * @param lightShadowColor Light directional shadow color.
 * @param darkShadowColor Dark directional shadow color.
 * @param shadowRadius Blur radius of the shadows.
 * @param shadowDistance Distance of directional shadows.
 * @param contentPadding Padding around card content.
 * @param enabled Whether clicking is enabled.
 * @param onClick Optional click callback.
 * @param content Card content.
 */
@Composable
fun KosmosNeumorphicCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(24.dp),
    style: KosmosNeumorphicStyle = KosmosNeumorphicStyle.Raised,

    surfaceColor: Color = Color(0xFFE0E5EC),
    lightShadowColor: Color = Color.White,
    darkShadowColor: Color = Color(0xFFA3B1C6),

    shadowRadius: Dp = 12.dp,
    shadowDistance: Dp = 8.dp,

    contentPadding: PaddingValues = PaddingValues(20.dp),

    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,

    content: @Composable () -> Unit
) {
    val safeRadius = shadowRadius.coerceAtLeast(0.dp)
    val safeDistance = shadowDistance.coerceAtLeast(0.dp)

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val raisedModifier = Modifier
        .dropShadow(shape) {
            radius = safeRadius.toPx()
            color = darkShadowColor.copy(alpha = 0.85f)
            offset = Offset(
                safeDistance.toPx(),
                safeDistance.toPx()
            )
        }
        .dropShadow(shape) {
            radius = safeRadius.toPx()
            color = lightShadowColor.copy(alpha = 0.95f)
            offset = Offset(
                -safeDistance.toPx(),
                -safeDistance.toPx()
            )
        }
        .background(surfaceColor, shape)
        .clip(shape)

    val pressedModifier = Modifier
        .background(surfaceColor, shape)
        .clip(shape)
        .innerShadow(shape) {
            radius = safeRadius.toPx()
            color = darkShadowColor.copy(alpha = 0.75f)
            offset = Offset(
                safeDistance.toPx(),
                safeDistance.toPx()
            )
        }
        .innerShadow(shape) {
            radius = safeRadius.toPx()
            color = lightShadowColor.copy(alpha = 0.95f)
            offset = Offset(
                -safeDistance.toPx(),
                -safeDistance.toPx()
            )
        }

    val flatModifier = Modifier
        .background(surfaceColor, shape)
        .border(
            width = 1.dp,
            color = darkShadowColor.copy(alpha = 0.15f),
            shape = shape
        )
        .clip(shape)

    val appearanceModifier = when (style) {
        KosmosNeumorphicStyle.Raised -> raisedModifier
        KosmosNeumorphicStyle.Flat -> flatModifier
        KosmosNeumorphicStyle.Pressed -> pressedModifier
    }

    val cardModifier = modifier.then(appearanceModifier)

    Box(
        modifier = if (onClick != null) {
            cardModifier
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(contentPadding)
        } else {
            cardModifier.padding(contentPadding)
        }
    ) {
        content()
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosNeumorphicCard gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosNeumorphicCardPreview() {
    PreviewGallery("KosmosNeumorphicCard") {
        PreviewFrame("Raised neumorphic card", "Outer shadows lift the card from its matching surface.", light = false) {
            Box(Modifier.fillMaxWidth().background(Color(0xFFE0E5EC)).padding(24.dp)) { KosmosNeumorphicCard(style = KosmosNeumorphicStyle.Raised) { Column { Text("Raised surface", color = Color(0xFF303040)); Text("Directional shadows", color = Color(0xFF555566)) } } }
        }

        PreviewFrame("Flat neumorphic card", "Bordered flat surface without depth.", light = false) {
            Box(Modifier.fillMaxWidth().background(Color(0xFFE0E5EC)).padding(24.dp)) { KosmosNeumorphicCard(style = KosmosNeumorphicStyle.Flat) { Column { Text("Flat surface", color = Color(0xFF303040)); Text("Directional shadows", color = Color(0xFF555566)) } } }
        }

        PreviewFrame("Pressed neumorphic card", "Inner shadows create a recessed surface.", light = false) {
            Box(Modifier.fillMaxWidth().background(Color(0xFFE0E5EC)).padding(24.dp)) { KosmosNeumorphicCard(style = KosmosNeumorphicStyle.Pressed) { Column { Text("Pressed surface", color = Color(0xFF303040)); Text("Directional shadows", color = Color(0xFF555566)) } } }
        }

    }
}
