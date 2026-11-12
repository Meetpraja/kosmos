package com.kosmos.components.inputs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.kosmos.components.preview.PreviewFrame
import com.kosmos.components.preview.PreviewGallery

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Text
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Direction in which a card was swiped.
 */
enum class KosmosCardSwipeDirection {
    Left,
    Right
}

/**
 * A customizable swipeable card stack.
 *
 * The top card can be dragged left or right. Cards underneath
 * automatically scale and shift to create a stacked appearance.
 *
 * @param cards List of items displayed in the stack.
 * @param onCardSwiped Called when a card is successfully swiped.
 * @param modifier Modifier applied to the complete stack.
 * @param cardHeight Height of each card.
 * @param visibleCardCount Number of cards visible in the stack.
 * @param stackOffset Vertical spacing between stacked cards.
 * @param scaleStep Scale reduction for each card behind the top card.
 * @param swipeThreshold Fraction of available width needed to dismiss.
 * @param cardShape Shape applied to every card.
 * @param cardColor Background color of every card.
 * @param cardElevation Shadow elevation of the cards.
 * @param enabled Whether swipe gestures are enabled.
 * @param content Composable content displayed inside each card.
 */
@Composable
fun <T> KosmosCardStack(
    cards: List<T>,
    onCardSwiped: (T, KosmosCardSwipeDirection) -> Unit,
    modifier: Modifier = Modifier,
    cardHeight: Dp = 360.dp,
    visibleCardCount: Int = 3,
    stackOffset: Dp = 16.dp,
    scaleStep: Float = 0.06f,
    swipeThreshold: Float = 0.25f,
    cardShape: Shape = RoundedCornerShape(28.dp),
    cardColor: Color = Color(0xFF24242E),
    cardElevation: Dp = 8.dp,
    enabled: Boolean = true,
    content: @Composable (T) -> Unit
) {
    require(visibleCardCount >= 1) {
        "visibleCardCount must be at least 1."
    }

    require(swipeThreshold in 0f..1f) {
        "swipeThreshold must be between 0 and 1."
    }

    require(scaleStep in 0f..1f) {
        "scaleStep must be between 0 and 1."
    }

    BoxWithConstraints(
        modifier = modifier.height(
            cardHeight + stackOffset * (visibleCardCount - 1)
        )
    ) {
        val density = LocalDensity.current
        val scope = rememberCoroutineScope()

        val widthPx = with(density) {
            maxWidth.toPx()
        }

        val stackOffsetPx = with(density) {
            stackOffset.toPx()
        }

        var currentIndex by remember {
            mutableIntStateOf(0)
        }

        val offsetX = remember {
            Animatable(0f)
        }

        // Reset the stack when the supplied list changes.
        LaunchedEffect(cards) {
            currentIndex = 0
            offsetX.snapTo(0f)
        }

        if (currentIndex < cards.size) {

            // Draw the cards behind first.
            for (depth in (visibleCardCount - 1) downTo 0) {

                val itemIndex = currentIndex + depth

                if (itemIndex < cards.size) {

                    val isTopCard = depth == 0
                    val cardItem = cards[itemIndex]

                    val scale = (1f - depth * scaleStep)
                        .coerceAtLeast(0.1f)

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .height(cardHeight)
                            .graphicsLayer {
                                translationY = depth * stackOffsetPx
                                scaleX = scale
                                scaleY = scale

                                if (isTopCard) {
                                    translationX = offsetX.value

                                    rotationZ = if (widthPx > 0f) {
                                        (offsetX.value / widthPx) * 12f
                                    } else {
                                        0f
                                    }
                                }
                            }
                            .shadow(
                                elevation = cardElevation,
                                shape = cardShape
                            )
                            .clip(cardShape)
                            .background(cardColor)
                            .then(
                                if (isTopCard && enabled) {
                                    Modifier.pointerInput(
                                        currentIndex,
                                        cards.size,
                                        enabled
                                    ) {
                                        detectDragGestures(
                                            onDrag = { change, dragAmount ->
                                                change.consume()

                                                scope.launch {
                                                    offsetX.snapTo(
                                                        offsetX.value +
                                                                dragAmount.x
                                                    )
                                                }
                                            },
                                            onDragEnd = {
                                                scope.launch {
                                                    val currentOffset =
                                                        offsetX.value

                                                    val threshold =
                                                        widthPx * swipeThreshold

                                                    if (abs(currentOffset) >= threshold) {

                                                        val direction =
                                                            if (currentOffset < 0f) {
                                                                KosmosCardSwipeDirection.Left
                                                            } else {
                                                                KosmosCardSwipeDirection.Right
                                                            }

                                                        val targetX =
                                                            if (direction == KosmosCardSwipeDirection.Left) {
                                                                -widthPx * 1.3f
                                                            } else {
                                                                widthPx * 1.3f
                                                            }

                                                        offsetX.animateTo(
                                                            targetValue = targetX,
                                                            animationSpec = spring(
                                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                                                stiffness = Spring.StiffnessMedium
                                                            )
                                                        )

                                                        currentIndex++

                                                        offsetX.snapTo(0f)

                                                        onCardSwiped(
                                                            cardItem,
                                                            direction
                                                        )

                                                    } else {
                                                        offsetX.animateTo(
                                                            targetValue = 0f,
                                                            animationSpec = spring(
                                                                dampingRatio = Spring.DampingRatioNoBouncy,
                                                                stiffness = Spring.StiffnessMedium
                                                            )
                                                        )
                                                    }
                                                }
                                            },
                                            onDragCancel = {
                                                scope.launch {
                                                    offsetX.animateTo(0f)
                                                }
                                            }
                                        )
                                    }
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        content(cardItem)
                    }
                }
            }
        }
    }
}

/** All styles and interaction examples together. Enable Interactive Preview to explore. */
@Preview(name = "KosmosCardStack gallery", group = "Kosmos components", widthDp = 400, heightDp = 1000, showBackground = true)
@Composable
private fun KosmosCardStackPreview() {
    PreviewGallery("KosmosCardStack") {
        PreviewFrame("Swipeable card deck", "Drag left or right in Interactive Preview.", light = false) {
            val cards = remember { listOf("Explore the cosmos", "Find your next idea", "Build something new") }
            var lastSwipe by remember { mutableStateOf("Swipe a card") }
            Text(lastSwipe)
            KosmosCardStack(cards = cards, onCardSwiped = { item, direction -> lastSwipe = "$item: $direction" }, modifier = Modifier.fillMaxWidth(), cardHeight = 220.dp) { item -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(item, color = Color.White) } }
        }

        PreviewFrame("Compact cyan deck", "Four visible cards with deeper spacing.", light = false) {
            KosmosCardStack(cards = listOf("One", "Two", "Three", "Four"), onCardSwiped = { _, _ -> }, modifier = Modifier.fillMaxWidth(), cardHeight = 160.dp, visibleCardCount = 4, stackOffset = 20.dp, scaleStep = 0.1f, cardColor = Color(0xFF155E75)) { Text(it, Modifier.padding(24.dp), color = Color.White) }
        }

        PreviewFrame("Single disabled card", "No gestures, squared corners and custom content.", light = false) {
            KosmosCardStack(cards = listOf("Read only card"), onCardSwiped = { _, _ -> }, modifier = Modifier.fillMaxWidth(), visibleCardCount = 1, cardHeight = 160.dp, enabled = false, cardShape = RoundedCornerShape(8.dp)) { Text(it, Modifier.padding(24.dp), color = Color.White) }
        }

    }
}
