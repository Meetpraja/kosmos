
package com.kosmos.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Arrangement styles supported by KosmosRadialMenu.
 */
enum class KosmosRadialLayout {
    Circle,
    HalfCircle,
    QuarterCircle,
    Custom
}

/**
 * An individual radial menu action.
 */
data class KosmosRadialAction(
    val contentDescription: String,
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit
)

/**
 * Customizable animated radial action menu.
 *
 * Supports circular, semicircular, quarter-circle and custom layouts.
 */
@Composable
fun KosmosRadialMenu(
    actions: List<KosmosRadialAction>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

    menuIcon: @Composable () -> Unit,
    closeIcon: @Composable () -> Unit,

    layout: KosmosRadialLayout = KosmosRadialLayout.Circle,

    mainButtonColor: Color = Color(0xFFB56BFF),
    actionButtonColor: Color = Color(0xFF25252D),

    backgroundColor: Color = Color.Transparent,
    backgroundShape: Shape = CircleShape,

    mainButtonSize: Dp = 54.dp,
    actionButtonSize: Dp = 42.dp,
    radius: Dp = 76.dp,

    startAngle: Float = -90f,
    endAngle: Float = 270f,

    showActionShadow: Boolean = true,
    showMainShadow: Boolean = true,

    enabled: Boolean = true
) {
    val safeRadius = radius.coerceAtLeast(0.dp)
    val safeMainSize = mainButtonSize.coerceAtLeast(1.dp)
    val safeActionSize = actionButtonSize.coerceAtLeast(1.dp)

    val menuSize = safeRadius * 2 + safeActionSize

    Box(
        modifier = modifier
            .size(menuSize)
            .clip(backgroundShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {


        val menuProgress by animateFloatAsState(
            targetValue = if (expanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = 280,
                easing = FastOutSlowInEasing
            ),
            label = "KosmosRadialMenu"
        )
        val itemProgress = menuProgress

        actions.forEachIndexed { index, action ->


            val angles = when (layout) {
                KosmosRadialLayout.Circle -> -90f..270f
                KosmosRadialLayout.HalfCircle -> -180f..0f
                KosmosRadialLayout.QuarterCircle -> -90f..0f
                KosmosRadialLayout.Custom -> startAngle..endAngle
            }

            val angle = if (actions.size <= 1) {
                (angles.start + angles.endInclusive) / 2f
            } else {
                angles.start +
                        (angles.endInclusive - angles.start) *
                        index / (if (layout == KosmosRadialLayout.Circle) actions.size else actions.size - 1).toFloat()
            }

            val radians = angle * PI.toFloat() / 180f

            val x = safeRadius * cos(radians) * itemProgress
            val y = safeRadius * sin(radians) * itemProgress

            Box(
                modifier = Modifier
                    .align(Alignment.Center)

                    .size(safeActionSize)
                    .graphicsLayer {
                        translationX = x.toPx()
                        translationY = y.toPx()

                        alpha = itemProgress

                        val scale = 0.85f + (0.15f * itemProgress)
                        scaleX = scale
                        scaleY = scale
                    }
                    .then(
                        if (showActionShadow) {
                            Modifier.shadow(
                                elevation = 5.dp,
                                shape = CircleShape
                            )
                        } else {
                            Modifier
                        }
                    )
                    .clip(CircleShape)
                    .background(actionButtonColor)
                    .semantics {
                        contentDescription = action.contentDescription
                    }
                    .clickable(
                        enabled = enabled && expanded && itemProgress > 0.95f,
                        role = Role.Button,
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        action.onClick()
                        onExpandedChange(false)
                    },
                contentAlignment = Alignment.Center
            ) {
                action.icon()
            }
        }

        // Main action button
        Box(
            modifier = Modifier
                .size(safeMainSize)
                .then(
                    if (showMainShadow) {
                        Modifier.shadow(
                            elevation = 8.dp,
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(CircleShape)
                .background(mainButtonColor)
                .clickable(
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    onExpandedChange(!expanded)
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationZ = menuProgress * 45f
                },
                contentAlignment = Alignment.Center
            ) {
                if (expanded) closeIcon() else menuIcon()
            }
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosRadialMenu gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosRadialMenuPreview() {
    PreviewGallery("KosmosRadialMenu") {
        PreviewFrame("Closed radial menu", "Tap the main button and actions in Interactive Preview.", light = false) {
            var expanded by remember { mutableStateOf(false) }
            var selected by remember { mutableStateOf("Choose an action") }
            Text(selected)
            KosmosRadialMenu(
                actions = listOf("A", "B", "C", "D").map { label -> KosmosRadialAction(label, { Text(label, color = Color.White) }, { selected = "Selected $label" }) },
                expanded = expanded, onExpandedChange = { expanded = it },
                menuIcon = { Text("+", color = Color.White) }, closeIcon = { Text("×", color = Color.White) },
                layout = KosmosRadialLayout.Circle, startAngle = 20f, endAngle = 160f,
                radius = 90.dp
            )
        }

        PreviewFrame("Full circle actions", "Tap the main button and actions in Interactive Preview.", light = false) {
            var expanded by remember { mutableStateOf(true) }
            var selected by remember { mutableStateOf("Choose an action") }
            Text(selected)
            KosmosRadialMenu(
                actions = listOf("A", "B", "C", "D").map { label -> KosmosRadialAction(label, { Text(label, color = Color.White) }, { selected = "Selected $label" }) },
                expanded = expanded, onExpandedChange = { expanded = it },
                menuIcon = { Text("+", color = Color.White) }, closeIcon = { Text("×", color = Color.White) },
                layout = KosmosRadialLayout.Circle, startAngle = 20f, endAngle = 160f,
                radius = 90.dp
            )
        }

        PreviewFrame("Semicircle actions", "Tap the main button and actions in Interactive Preview.", light = false) {
            var expanded by remember { mutableStateOf(true) }
            var selected by remember { mutableStateOf("Choose an action") }
            Text(selected)
            KosmosRadialMenu(
                actions = listOf("A", "B", "C", "D").map { label -> KosmosRadialAction(label, { Text(label, color = Color.White) }, { selected = "Selected $label" }) },
                expanded = expanded, onExpandedChange = { expanded = it },
                menuIcon = { Text("+", color = Color.White) }, closeIcon = { Text("×", color = Color.White) },
                layout = KosmosRadialLayout.HalfCircle, startAngle = 20f, endAngle = 160f,
                radius = 90.dp
            )
        }

        PreviewFrame("Quarter circle actions", "Tap the main button and actions in Interactive Preview.", light = false) {
            var expanded by remember { mutableStateOf(true) }
            var selected by remember { mutableStateOf("Choose an action") }
            Text(selected)
            KosmosRadialMenu(
                actions = listOf("A", "B", "C", "D").map { label -> KosmosRadialAction(label, { Text(label, color = Color.White) }, { selected = "Selected $label" }) },
                expanded = expanded, onExpandedChange = { expanded = it },
                menuIcon = { Text("+", color = Color.White) }, closeIcon = { Text("×", color = Color.White) },
                layout = KosmosRadialLayout.QuarterCircle, startAngle = 20f, endAngle = 160f,
                radius = 90.dp
            )
        }

        PreviewFrame("Custom arc actions", "Tap the main button and actions in Interactive Preview.", light = false) {
            var expanded by remember { mutableStateOf(true) }
            var selected by remember { mutableStateOf("Choose an action") }
            Text(selected)
            KosmosRadialMenu(
                actions = listOf("A", "B", "C", "D").map { label -> KosmosRadialAction(label, { Text(label, color = Color.White) }, { selected = "Selected $label" }) },
                expanded = expanded, onExpandedChange = { expanded = it },
                menuIcon = { Text("+", color = Color.White) }, closeIcon = { Text("×", color = Color.White) },
                layout = KosmosRadialLayout.Custom, startAngle = 20f, endAngle = 160f,
                radius = 90.dp
            )
        }

    }
}
