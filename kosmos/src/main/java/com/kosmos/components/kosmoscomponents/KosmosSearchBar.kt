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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
/**
 * A customizable search bar for Jetpack Compose.
 *
 * Supports custom leading and trailing content, placeholder text,
 * clear functionality, keyboard configuration and custom styling.
 *
 * @param value Current search query.
 * @param onValueChange Callback invoked when the query changes.
 * @param modifier Modifier used to control size and positioning.
 * @param placeholder Placeholder displayed when the query is empty.
 * @param enabled Whether the search field accepts input.
 * @param readOnly Whether the text field is read-only.
 * @param shape Shape of the search bar.
 * @param backgroundColor Background color of the search bar.
 * @param textColor Color of the entered text.
 * @param placeholderColor Color of the placeholder.
 * @param borderColor Color of the search bar border.
 * @param borderWidth Thickness of the search bar border.
 * @param cursorColor Color of the text cursor.
 * @param contentPadding Padding around the search bar content.
 * @param leadingIcon Optional leading composable content.
 * @param trailingIcon Optional trailing composable content.
 * @param showClearButton Whether to show a clear button when text exists.
 * @param clearButtonColor Color of the clear button.
 * @param textStyle Style applied to the entered text.
 * @param keyboardOptions Keyboard configuration.
 * @param keyboardActions Keyboard action callbacks.
 */
@Composable
fun KosmosSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    enabled: Boolean = true,
    readOnly: Boolean = false,

    // Appearance
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = Color(0xFF191923),
    textColor: Color = Color.White,
    placeholderColor: Color = Color(0xFF85858F),
    borderColor: Color = Color(0xFF343440),
    borderWidth: Dp = 1.dp,
    cursorColor: Color = Color(0xFFB56BFF),
    contentPadding: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 14.dp
    ),

    // Content
    leadingIcon: (@Composable RowScope.() -> Unit)? = null,
    trailingIcon: (@Composable RowScope.() -> Unit)? = null,
    showClearButton: Boolean = true,
    clearButtonColor: Color = Color(0xFFAAAAAA),

    // Text configuration
    textStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = textColor
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val safeBorderWidth = borderWidth.coerceAtLeast(0.dp)

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .clip(shape)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = safeBorderWidth,
                color = borderColor,
                shape = shape
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Leading content
        if (leadingIcon != null) {
            leadingIcon()

            Spacer(modifier = Modifier.width(12.dp))
        }

        // Search input
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = true,
            textStyle = textStyle.copy(color = textColor),
            cursorBrush = SolidColor(cursorColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->

                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = placeholderColor,
                            fontSize = textStyle.fontSize,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    innerTextField()
                }
            }
        )

        // Clear button
        if (showClearButton && value.isNotEmpty() && enabled && !readOnly) {
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        onValueChange("")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "×",
                    color = clearButtonColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        // Custom trailing content
        if (trailingIcon != null) {
            Spacer(modifier = Modifier.width(10.dp))
            trailingIcon()
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosSearchBar gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosSearchBarPreview() {
    PreviewGallery("KosmosSearchBar") {
        PreviewFrame("Empty search input", "Interactive Preview supports typing.", light = false) {
            var query by remember { mutableStateOf("") }
            KosmosSearchBar(value = query, onValueChange = { query = it })
        }

        PreviewFrame("Populated search with slots", "Clear button and custom leading and trailing content.", light = false) {
            var query by remember { mutableStateOf("Compose") }
            KosmosSearchBar(value = query, onValueChange = { query = it }, leadingIcon = { Text("⌕") }, trailingIcon = { Text("Filter") })
        }

        PreviewFrame("Light disabled search", "Custom light colors with disabled input.", light = true) {
            KosmosSearchBar(value = "Search unavailable", onValueChange = {}, enabled = false, backgroundColor = Color(0xFFF1F5F9), textColor = Color.Gray, shape = CircleShape)
        }

    }
}
