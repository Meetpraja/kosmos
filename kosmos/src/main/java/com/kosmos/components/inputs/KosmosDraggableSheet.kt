package com.kosmos.components.inputs

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

/**
 * A customizable draggable bottom sheet for Kosmos.
 *
 * Supports controlled expanded/collapsed states, configurable peek height,
 * sheet height, corner shape, scrim, drag handle and spring animation.
 *
 * The parent owns the expanded state.
 *
 * @param expanded Whether the sheet is currently expanded.
 * @param onExpandedChange Called when the user changes the sheet state.
 * @param modifier Modifier applied to the complete component.
 * @param peekHeight Visible sheet height while collapsed.
 * @param sheetMaxHeight Maximum sheet height, limited by available space.
 * @param sheetShape Shape of the top of the sheet.
 * @param sheetColor Background color of the sheet.
 * @param scrimColor Color drawn behind the expanded sheet.
 * @param scrimEnabled Whether the background scrim is displayed.
 * @param dismissOnScrimClick Whether tapping the scrim collapses the sheet.
 * @param dragHandle Whether the default drag handle is displayed.
 * @param dragHandleContent Optional custom drag handle.
 * @param positionalThreshold Fraction of travel required to change state.
 * @param velocityThreshold Minimum fling velocity in dp per second.
 * @param enabled Whether dragging is enabled.
 * @param content Background content behind the sheet.
 * @param sheetContent Content displayed inside the sheet.
 */
@Composable
fun KosmosDraggableSheet(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    peekHeight: Dp = 100.dp,
    sheetMaxHeight: Dp = 640.dp,
    sheetShape: Shape = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp
    ),
    sheetColor: Color = Color(0xFF202027),
    scrimColor: Color = Color.Black,
    scrimEnabled: Boolean = true,
    dismissOnScrimClick: Boolean = true,
    dragHandle: Boolean = true,
    dragHandleContent: (@Composable () -> Unit)? = null,
    positionalThreshold: Float = 0.5f,
    velocityThreshold: Dp = 900.dp,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
    sheetContent: @Composable ColumnScope.() -> Unit
) {
    require(peekHeight >= 0.dp) {
        "peekHeight cannot be negative."
    }

    require(sheetMaxHeight > 0.dp) {
        "sheetMaxHeight must be greater than zero."
    }

    require(positionalThreshold in 0f..1f) {
        "positionalThreshold must be between 0 and 1."
    }

    require(velocityThreshold >= 0.dp) {
        "velocityThreshold cannot be negative."
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        val density = LocalDensity.current

        val safeSheetHeight = sheetMaxHeight.coerceAtMost(maxHeight)
        val safePeekHeight = peekHeight.coerceAtMost(safeSheetHeight)

        val sheetHeightPx = with(density) {
            safeSheetHeight.toPx()
        }

        val peekHeightPx = with(density) {
            safePeekHeight.toPx()
        }

        val collapsedOffset = (sheetHeightPx - peekHeightPx)
            .coerceAtLeast(0f)

        val velocityThresholdPx = with(density) {
            velocityThreshold.toPx()
        }

        var offsetPx by remember {
            mutableFloatStateOf(
                if (expanded) 0f else collapsedOffset
            )
        }

        var isDragging by remember {
            mutableStateOf(false)
        }

        // Keep the component synchronized with its external state.
        LaunchedEffect(expanded, collapsedOffset) {
            if (!isDragging) {
                offsetPx = if (expanded) 0f else collapsedOffset
            }
        }

        val animatedOffset by animateFloatAsState(
            targetValue = offsetPx.coerceIn(0f, collapsedOffset),
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow
            ),
            label = "KosmosDraggableSheet"
        )

        val dragState = rememberDraggableState { delta ->
            if (enabled) {
                offsetPx = (offsetPx + delta)
                    .coerceIn(0f, collapsedOffset)
            }
        }

        val expansionProgress = if (collapsedOffset > 0f) {
            1f - (animatedOffset / collapsedOffset)
        } else {
            1f
        }.coerceIn(0f, 1f)

        // Background content.
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }

        // Scrim behind the sheet.
        if (scrimEnabled && expansionProgress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = expansionProgress * 0.48f
                    }
                    .background(scrimColor)
                    .then(
                        if (dismissOnScrimClick && expanded) {
                            Modifier.clickable {
                                onExpandedChange(false)
                            }
                        } else {
                            Modifier
                        }
                    )
            )
        }

        // Draggable sheet.
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(safeSheetHeight)
                .graphicsLayer {
                    translationY = animatedOffset
                }
                .clip(sheetShape)
                .background(sheetColor)
                .semantics {
                    stateDescription = if (expanded) {
                        "Sheet expanded"
                    } else {
                        "Sheet collapsed"
                    }

                    customActions = listOf(
                        CustomAccessibilityAction(
                            label = if (expanded) {
                                "Collapse sheet"
                            } else {
                                "Expand sheet"
                            }
                        ) {
                            onExpandedChange(!expanded)
                            true
                        }
                    )
                }
                .draggable(
                    state = dragState,
                    orientation = Orientation.Vertical,
                    enabled = enabled,
                    onDragStarted = {
                        isDragging = true
                        offsetPx = animatedOffset
                    },
                    onDragStopped = { velocity ->

                        isDragging = false

                        val shouldExpand = when {
                            abs(velocity) > velocityThresholdPx ->
                                velocity < 0f

                            else ->
                                offsetPx <= collapsedOffset *
                                        (1f - positionalThreshold)
                        }

                        offsetPx = if (shouldExpand) {
                            0f
                        } else {
                            collapsedOffset
                        }

                        onExpandedChange(shouldExpand)
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Drag handle.
            if (dragHandle) {
                if (dragHandleContent != null) {
                    dragHandleContent()
                } else {
                    DefaultKosmosDragHandle()
                }
            }

            // User content.
            sheetContent()
        }
    }
}

/**
 * Default minimal drag handle.
 */
@Composable
private fun DefaultKosmosDragHandle() {
    Spacer(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .width(38.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.White.copy(alpha = 0.28f))
    )
}
