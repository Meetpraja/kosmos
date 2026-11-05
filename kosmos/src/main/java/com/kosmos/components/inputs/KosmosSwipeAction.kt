
package com.kosmos.components.inputs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

/**
 * Logical side from which swipe actions are revealed.
 */
enum class KosmosSwipeSide {
    Start,
    End
}

/**
 * Represents an individual action inside KosmosSwipeAction.
 *
 * @param label Visible action label.
 * @param icon Composable icon displayed above the label.
 * @param onClick Callback invoked when the action is selected.
 * @param backgroundColor Background color of the action.
 * @param contentColor Icon and label color.
 */
data class KosmosSwipeActionItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit,
    val backgroundColor: Color = Color(0xFF292932),
    val contentColor: Color = Color.White
)

/**
 * A customizable swipe-to-reveal action container.
 *
 * Supports start-side and end-side actions, configurable spacing,
 * custom shapes, controlled reveal state, RTL layouts and animations.
 *
 * The parent owns the revealed state. Pass null to close the menu.
 *
 * @param revealedSide Currently revealed side, or null when closed.
 * @param onRevealedSideChange Callback when the revealed side changes.
 * @param startActions Actions revealed by swiping toward the end.
 * @param endActions Actions revealed by swiping toward the start.
 * @param modifier Modifier applied to the container.
 * @param actionWidth Width of each action button.
 * @param actionSpacing Space between individual action buttons.
 * @param actionCornerRadius Corner radius of individual action buttons.
 * @param animationDurationMillis Duration of the settling animation.
 * @param revealThreshold Fraction of the action width required to reveal.
 * @param velocityThreshold Minimum fling velocity for directional settling.
 * @param enabled Whether gestures and actions are enabled.
 * @param contentShape Shape used to clip the foreground content.
 * @param content Foreground content that the user swipes.
 */
@Composable
fun KosmosSwipeAction(
    revealedSide: KosmosSwipeSide?,
    onRevealedSideChange: (KosmosSwipeSide?) -> Unit,
    modifier: Modifier = Modifier,
    startActions: List<KosmosSwipeActionItem> = emptyList(),
    endActions: List<KosmosSwipeActionItem> = emptyList(),
    actionWidth: Dp = 76.dp,
    actionSpacing: Dp = 6.dp,
    actionCornerRadius: Dp = 8.dp,
    animationDurationMillis: Int = 220,
    revealThreshold: Float = 0.5f,
    velocityThreshold: Dp = 900.dp,
    enabled: Boolean = true,
    contentShape: Shape = RoundedCornerShape(12.dp),
    content: @Composable () -> Unit
) {
    require(revealThreshold in 0f..1f) {
        "revealThreshold must be between 0 and 1."
    }

    require(animationDurationMillis >= 0) {
        "animationDurationMillis cannot be negative."
    }

    require(actionWidth > 0.dp) {
        "actionWidth must be greater than zero."
    }

    require(actionSpacing >= 0.dp) {
        "actionSpacing cannot be negative."
    }

    if (startActions.isEmpty() && endActions.isEmpty()) {
        content()
        return
    }

    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val directionSign = if (
        layoutDirection == LayoutDirection.Ltr
    ) 1f else -1f

    val safeCornerRadius = actionCornerRadius.coerceAtLeast(0.dp)
    val safeVelocityThreshold = velocityThreshold.coerceAtLeast(0.dp)

    fun calculateActionWidth(count: Int): Dp {
        if (count == 0) return 0.dp

        return actionWidth * count +
                actionSpacing * (count - 1)
    }

    val startTotalWidth = calculateActionWidth(startActions.size)
    val endTotalWidth = calculateActionWidth(endActions.size)

    val startWidthPx = with(density) {
        startTotalWidth.toPx()
    }

    val endWidthPx = with(density) {
        endTotalWidth.toPx()
    }

    val velocityThresholdPx = with(density) {
        safeVelocityThreshold.toPx()
    }

    var offsetPx by remember {
        mutableFloatStateOf(0f)
    }

    var isDragging by remember {
        mutableStateOf(false)
    }

    // Synchronize externally controlled state and handle action-list changes.
    LaunchedEffect(
        revealedSide,
        startWidthPx,
        endWidthPx
    ) {
        val targetOffset = when (revealedSide) {
            KosmosSwipeSide.Start -> -startWidthPx
            KosmosSwipeSide.End -> endWidthPx
            null -> 0f
        }

        if (isDragging) {
            offsetPx = offsetPx.coerceIn(
                -startWidthPx,
                endWidthPx
            )
        } else {
            offsetPx = targetOffset
        }
    }

    val animatedOffset by animateFloatAsState(
        targetValue = offsetPx.coerceIn(
            -startWidthPx,
            endWidthPx
        ),
        animationSpec = if (isDragging) {
            snap()
        } else {
            tween(
                durationMillis = animationDurationMillis,
                easing = FastOutSlowInEasing
            )
        },
        label = "KosmosSwipeOffset"
    )

    val dragState = rememberDraggableState { delta ->
        if (enabled) {
            val currentOffset = offsetPx

            val newOffset = (
                    currentOffset - delta * directionSign
                    ).coerceIn(-startWidthPx, endWidthPx)

            // Do not allow one gesture to cross the center
            // and reveal the opposite side.
            offsetPx = when {
                currentOffset < 0f && newOffset > 0f -> 0f
                currentOffset > 0f && newOffset < 0f -> 0f
                else -> newOffset
            }
        }
    }


    val startFullyRevealed =
        startWidthPx > 0f &&
                animatedOffset <= -startWidthPx + 1f

    val endFullyRevealed =
        endWidthPx > 0f &&
                animatedOffset >= endWidthPx - 1f

    val accessibilityActions = buildList {
        if (revealedSide == null) {
            if (startActions.isNotEmpty()) {
                add(
                    CustomAccessibilityAction("Reveal start actions") {
                        if (enabled) {
                            onRevealedSideChange(KosmosSwipeSide.Start)
                            true
                        } else {
                            false
                        }
                    }
                )
            }

            if (endActions.isNotEmpty()) {
                add(
                    CustomAccessibilityAction("Reveal end actions") {
                        if (enabled) {
                            onRevealedSideChange(KosmosSwipeSide.End)
                            true
                        } else {
                            false
                        }
                    }
                )
            }
        } else {
            add(
                CustomAccessibilityAction("Close swipe actions") {
                    onRevealedSideChange(null)
                    true
                }
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
    ) {

        // Background action layer.
        // matchParentSize ensures it follows the foreground measurement.
        Box(
            modifier = Modifier.matchParentSize()
        ) {

            // Start-side actions.
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(startTotalWidth)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(actionSpacing)
            ) {
                startActions.forEach { action ->

                    Box(
                        modifier = Modifier
                            .width(actionWidth)
                            .fillMaxHeight()
                            .clip(
                                RoundedCornerShape(safeCornerRadius)
                            )
                            .background(action.backgroundColor)
                            .clickable(
                                enabled = enabled && startFullyRevealed
                            ) {
                                action.onClick()
                                onRevealedSideChange(null)
                            }
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ActionContent(action)
                    }
                }
            }

            // End-side actions.
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(endTotalWidth)
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(actionSpacing)
            ) {
                endActions.forEach { action ->

                    Box(
                        modifier = Modifier
                            .width(actionWidth)
                            .fillMaxHeight()
                            .clip(
                                RoundedCornerShape(safeCornerRadius)
                            )
                            .background(action.backgroundColor)
                            .clickable(
                                enabled = enabled && endFullyRevealed
                            ) {
                                action.onClick()
                                onRevealedSideChange(null)
                            }
                            .padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ActionContent(action)
                    }
                }
            }
        }

        // Foreground swipeable content.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationX = -animatedOffset * directionSign
                }
                .clip(contentShape)
                .semantics {
                    stateDescription = when (revealedSide) {
                        KosmosSwipeSide.Start -> "Start actions revealed"
                        KosmosSwipeSide.End -> "End actions revealed"
                        null -> "Swipe actions hidden"
                    }

                    customActions = accessibilityActions
                }
                .draggable(
                    state = dragState,
                    orientation = Orientation.Horizontal,
                    enabled = enabled,
                    onDragStarted = {
                        isDragging = true
                        offsetPx = animatedOffset
                    },

                    onDragStopped = { velocity ->

                        isDragging = false

                        val logicalVelocity = -velocity * directionSign

                        val shouldRevealEnd = when {
                            offsetPx > 0f -> {
                                if (abs(logicalVelocity) > velocityThresholdPx) {
                                    logicalVelocity > 0f
                                } else {
                                    offsetPx >= endWidthPx * revealThreshold
                                }
                            }

                            else -> false
                        }

                        val shouldRevealStart = when {
                            offsetPx < 0f -> {
                                if (abs(logicalVelocity) > velocityThresholdPx) {
                                    logicalVelocity < 0f
                                } else {
                                    abs(offsetPx) >= startWidthPx * revealThreshold
                                }
                            }

                            else -> false
                        }

                        val targetSide = when {
                            shouldRevealEnd -> KosmosSwipeSide.End
                            shouldRevealStart -> KosmosSwipeSide.Start
                            else -> null
                        }

                        offsetPx = when (targetSide) {
                            KosmosSwipeSide.Start -> -startWidthPx
                            KosmosSwipeSide.End -> endWidthPx
                            null -> 0f
                        }

                        onRevealedSideChange(targetSide)
                    }
                )
        ) {
            content()
        }
    }
}

/**
 * Default action content layout.
 */
@Composable
private fun ActionContent(
    action: KosmosSwipeActionItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        action.icon()

        Text(
            text = action.label,
            color = action.contentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}