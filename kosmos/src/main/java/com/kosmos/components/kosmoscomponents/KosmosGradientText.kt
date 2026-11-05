package com.kosmos.components.kosmoscomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


/**
 * Displays text with a customizable gradient brush.
 *
 * Supports custom typography, alignment, maximum lines
 * and overflow behavior.
 *
 * @param text Text to display.
 * @param modifier Modifier used to control size and positioning.
 * @param gradientColors Colors used to create the text gradient.
 * @param fontSize Size of the text.
 * @param fontWeight Weight of the text.
 * @param fontFamily Font family used by the text.
 * @param fontStyle Style of the text.
 * @param textAlign Alignment of the text.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Height of each text line.
 * @param maxLines Maximum number of lines to display.
 * @param overflow How overflowing text should be handled.
 * @param softWrap Whether text should wrap onto multiple lines.
 * @param style Additional text styling.
 */
@Composable
fun KosmosGradientText(
    text: String,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(
        Color(0xFFB45CFF),
        Color(0xFF6366F1),
        Color(0xFF38BDF8)
    ),
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily? = null,
    fontStyle: FontStyle? = null,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    style: TextStyle = TextStyle.Default
) {
    val safeColors = gradientColors.ifEmpty {
        listOf(Color.White, Color.LightGray)
    }

    val gradientBrush = remember(safeColors) {
        Brush.linearGradient(colors = safeColors)
    }

    textAlign?.let {
        Text(
            text = text,
            modifier = modifier,
            style = style.copy(
                brush = gradientBrush,
                fontSize = fontSize,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                fontStyle = fontStyle,
                textAlign = it,
                letterSpacing = letterSpacing,
                lineHeight = lineHeight
            ),
            maxLines = maxLines.coerceAtLeast(1),
            overflow = overflow,
            softWrap = softWrap
        )
    }
}