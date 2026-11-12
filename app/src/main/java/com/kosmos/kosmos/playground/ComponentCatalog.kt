package com.kosmos.kosmos.playground

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.compose.foundation.background
import com.kosmos.components.backgrounds.*
import com.kosmos.components.inputs.*
import com.kosmos.components.kosmoscomponents.*
import com.kosmos.components.loaders.*
import com.kosmos.components.shapes.*

internal val componentCatalog = listOf(
    ComponentSpec("KosmosGlassCard", "Glass card", "Surfaces", "Layer tinted translucent surfaces over your own backdrop.", "This is translucency, not a backdrop blur.", "kosmoscomponents", listOf(
        Control("shape", ControlKind.Dp, "24", 0.0f, 48.0f, demo = false),
        Control("backgroundColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("backgroundAlpha", ControlKind.Float, "0.08", 0.0f, 1.0f, demo = false),
        Control("borderColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("borderWidth", ControlKind.Dp, "1", 0.0f, 48.0f, demo = false),
        Control("contentPadding", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF553399", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosGradientButton", "Gradient button", "Surfaces", "Build a gradient call to action with custom spacing and shape.", "Tap the button to test the click callback.", "kosmoscomponents", listOf(
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("gradient1", ControlKind.Color, "FF8B5CF6", 0.0f, 1.0f, demo = false),
        Control("gradient2", ControlKind.Color, "FF6366F1", 0.0f, 1.0f, demo = false),
        Control("gradient3", ControlKind.Color, "FF22D3EE", 0.0f, 1.0f, demo = false),
        Control("shape", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("borderColor", ControlKind.Color, "00000000", 0.0f, 48.0f, demo = false),
        Control("borderWidth", ControlKind.Dp, "0", 0.0f, 48.0f, demo = false),
        Control("contentPadding", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("customSlot", ControlKind.Boolean, "false", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosGradientText", "Gradient text", "Surfaces", "Explore gradient typography, alignment and line spacing.", "Edit the sample text and adjust typography.", "kosmoscomponents", listOf(
        Control("text", ControlKind.Text, "Build with Kosmos", 0.0f, 48.0f, demo = false),
        Control("gradient1", ControlKind.Color, "FF8B5CF6", 0.0f, 1.0f, demo = false),
        Control("gradient2", ControlKind.Color, "FF6366F1", 0.0f, 1.0f, demo = false),
        Control("gradient3", ControlKind.Color, "FF22D3EE", 0.0f, 1.0f, demo = false),
        Control("fontSize", ControlKind.Sp, "24", 12.0f, 64.0f, demo = false),
        Control("fontWeight", ControlKind.Choice, "Bold", 0.0f, 48.0f, choices = listOf("Normal", "Medium", "Bold", "Black"), demo = false),
        Control("fontFamily", ControlKind.Choice, "Default", 0.0f, 48.0f, choices = listOf("Default", "SansSerif", "Serif", "Monospace"), demo = false),
        Control("fontStyle", ControlKind.Choice, "Normal", 0.0f, 48.0f, choices = listOf("Normal", "Italic"), demo = false),
        Control("textAlign", ControlKind.Choice, "Start", 0.0f, 48.0f, choices = listOf("Start", "Center", "End"), demo = false),
        Control("letterSpacing", ControlKind.Sp, "0", -2.0f, 8.0f, demo = false),
        Control("lineHeight", ControlKind.Sp, "30", 12.0f, 64.0f, demo = false),
        Control("maxLines", ControlKind.Int, "3", 1.0f, 8.0f, demo = false),
        Control("overflow", ControlKind.Choice, "Clip", 0.0f, 48.0f, choices = listOf("Clip", "Ellipsis", "Visible"), demo = false),
        Control("softWrap", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosNeumorphicCard", "Neumorphic card", "Surfaces", "Sculpt soft surfaces with raised, flat or pressed shadows.", "Tap the card to test its click callback.", "kosmoscomponents", listOf(
        Control("shape", ControlKind.Dp, "24", 0.0f, 48.0f, demo = false),
        Control("style", ControlKind.Choice, "Raised", 0.0f, 48.0f, choices = listOf("Raised", "Flat", "Pressed"), demo = false),
        Control("surfaceColor", ControlKind.Color, "FFE0E5EC", 0.0f, 48.0f, demo = false),
        Control("lightShadowColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("darkShadowColor", ControlKind.Color, "FFA3B1C6", 0.0f, 48.0f, demo = false),
        Control("shadowRadius", ControlKind.Dp, "12", 0.0f, 48.0f, demo = false),
        Control("shadowDistance", ControlKind.Dp, "8", 0.0f, 48.0f, demo = false),
        Control("contentPadding", ControlKind.Dp, "20", 0.0f, 48.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FFE0E5EC", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FF313448", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosSearchBar", "Search bar", "Surfaces", "Style an editable search field with optional leading and trailing slots.", "Type, clear the query, or try read-only and disabled states.", "kosmoscomponents", listOf(
        Control("placeholder", ControlKind.Text, "Search...", 0.0f, 48.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("readOnly", ControlKind.Boolean, "false", 0.0f, 48.0f, demo = false),
        Control("shape", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("backgroundColor", ControlKind.Color, "FF191923", 0.0f, 48.0f, demo = false),
        Control("textColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("placeholderColor", ControlKind.Color, "FF85858F", 0.0f, 48.0f, demo = false),
        Control("borderColor", ControlKind.Color, "FF343440", 0.0f, 48.0f, demo = false),
        Control("borderWidth", ControlKind.Dp, "1", 0.0f, 48.0f, demo = false),
        Control("cursorColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("contentPadding", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("showClearButton", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("clearButtonColor", ControlKind.Color, "FFAAAAAA", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("customSlot", ControlKind.Boolean, "false", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosCardStack", "Card stack", "Interactions", "Arrange a deck with configurable depth and swipe thresholds.", "Swipe left or right. Replay resets the deck.", "inputs", listOf(
        Control("cardHeight", ControlKind.Dp, "180", 80.0f, 280.0f, demo = false),
        Control("visibleCardCount", ControlKind.Int, "3", 1.0f, 5.0f, demo = false),
        Control("stackOffset", ControlKind.Dp, "16", 0.0f, 48.0f, demo = false),
        Control("scaleStep", ControlKind.Float, "0.06", 0.0f, 0.2f, demo = false),
        Control("swipeThreshold", ControlKind.Float, "0.25", 0.0f, 1.0f, demo = false),
        Control("cardShape", ControlKind.Dp, "28", 0.0f, 48.0f, demo = false),
        Control("cardColor", ControlKind.Color, "FF24242E", 0.0f, 48.0f, demo = false),
        Control("cardElevation", ControlKind.Dp, "8", 0.0f, 48.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("itemCount", ControlKind.Int, "3", 1.0f, 4.0f, demo = true),
    )),
    ComponentSpec("KosmosDraggableSheet", "Draggable sheet", "Interactions", "Configure a bottom sheet, its scrim and drag behavior.", "Drag the sheet or tap Open sheet and the scrim.", "inputs", listOf(
        Control("peekHeight", ControlKind.Dp, "100", 30.0f, 180.0f, demo = false),
        Control("sheetMaxHeight", ControlKind.Dp, "260", 80.0f, 280.0f, demo = false),
        Control("sheetShape", ControlKind.Dp, "24", 0.0f, 48.0f, demo = false),
        Control("sheetColor", ControlKind.Color, "FF202027", 0.0f, 48.0f, demo = false),
        Control("scrimColor", ControlKind.Color, "FF000000", 0.0f, 48.0f, demo = false),
        Control("scrimEnabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("dismissOnScrimClick", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("dragHandle", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("positionalThreshold", ControlKind.Float, "0.5", 0.0f, 1.0f, demo = false),
        Control("velocityThreshold", ControlKind.Dp, "900", 0.0f, 2000.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("customSlot", ControlKind.Boolean, "false", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosRadialMenu", "Radial menu", "Interactions", "Arrange actions around a central animated menu button.", "Open the menu, then tap one of the actions.", "inputs", listOf(
        Control("layout", ControlKind.Choice, "Circle", 0.0f, 48.0f, choices = listOf("Circle", "HalfCircle", "QuarterCircle", "Custom"), demo = false),
        Control("mainButtonColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("actionButtonColor", ControlKind.Color, "FF25252D", 0.0f, 48.0f, demo = false),
        Control("backgroundColor", ControlKind.Color, "00000000", 0.0f, 48.0f, demo = false),
        Control("backgroundShape", ControlKind.Dp, "24", 0.0f, 48.0f, demo = false),
        Control("mainButtonSize", ControlKind.Dp, "54", 28.0f, 72.0f, demo = false),
        Control("actionButtonSize", ControlKind.Dp, "42", 28.0f, 72.0f, demo = false),
        Control("radius", ControlKind.Dp, "76", 40.0f, 110.0f, demo = false),
        Control("startAngle", ControlKind.Float, "-90", -360.0f, 360.0f, demo = false),
        Control("endAngle", ControlKind.Float, "270", -360.0f, 360.0f, demo = false),
        Control("showActionShadow", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("showMainShadow", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("customSlot", ControlKind.Boolean, "false", 0.0f, 1.0f, demo = true),
        Control("itemCount", ControlKind.Int, "3", 1.0f, 4.0f, demo = true),
    )),
    ComponentSpec("KosmosSwipeAction", "Swipe actions", "Interactions", "Reveal start and end actions behind your content.", "Swipe horizontally or tap Save and Delete.", "inputs", listOf(
        Control("actionColor", ControlKind.Color, "FF5B47C8", demo = true),
        Control("actionWidth", ControlKind.Dp, "76", 45.0f, 100.0f, demo = false),
        Control("actionSpacing", ControlKind.Dp, "6", 0.0f, 48.0f, demo = false),
        Control("actionCornerRadius", ControlKind.Dp, "8", 0.0f, 48.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "220", 100.0f, 4000.0f, demo = false),
        Control("revealThreshold", ControlKind.Float, "0.5", 0.0f, 1.0f, demo = false),
        Control("velocityThreshold", ControlKind.Dp, "900", 0.0f, 2000.0f, demo = false),
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("contentShape", ControlKind.Dp, "12", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
        Control("itemCount", ControlKind.Int, "3", 1.0f, 4.0f, demo = true),
    )),
    ComponentSpec("KosmosToggle", "Toggle", "Interactions", "Tune a tactile switch with independent thumb and shadow colors.", "Tap the switch to change its checked state.", "inputs", listOf(
        Control("enabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("surfaceColor", ControlKind.Color, "FF17171D", 0.0f, 48.0f, demo = false),
        Control("activeThumbColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("inactiveThumbColor", ControlKind.Color, "FFEEEEF2", 0.0f, 48.0f, demo = false),
        Control("lightShadowColor", ControlKind.Color, "FF353540", 0.0f, 48.0f, demo = false),
        Control("darkShadowColor", ControlKind.Color, "FF09090D", 0.0f, 48.0f, demo = false),
        Control("disabledThumbColor", ControlKind.Color, "FF777780", 0.0f, 48.0f, demo = false),
        Control("trackWidth", ControlKind.Dp, "58", 50.0f, 120.0f, demo = false),
        Control("trackHeight", ControlKind.Dp, "34", 26.0f, 60.0f, demo = false),
        Control("thumbSize", ControlKind.Dp, "25", 12.0f, 44.0f, demo = false),
        Control("animationEnabled", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosBlob", "Blob", "Shapes", "Create soft organic silhouettes with control points.", "Change point count and irregularity to reshape the outline.", "shapes", listOf(
        Control("color", ControlKind.Color, "FF8B5CF6", 0.0f, 48.0f, demo = false),
        Control("pointCount", ControlKind.Int, "8", 4.0f, 20.0f, demo = false),
        Control("irregularity", ControlKind.Float, "0.22", 0.0f, 0.45f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosLiquidShape", "Liquid shape", "Shapes", "Shape a flowing divider with adjustable wave height and depth.", "Adjust the curve controls to reshape the surface.", "shapes", listOf(
        Control("color", ControlKind.Color, "FF7C3AED", 0.0f, 48.0f, demo = false),
        Control("curveHeight", ControlKind.Float, "0.45", 0.0f, 1.0f, demo = false),
        Control("curveDepth", ControlKind.Float, "0.25", 0.0f, 0.5f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosOrbit", "Orbit", "Shapes", "Combine orbital rings, glow, borders and moving satellites.", "Enable animation and orbiting objects to see motion.", "shapes", listOf(
        Control("orbitColor", ControlKind.Color, "FF8B5CF6", 0.0f, 48.0f, demo = false),
        Control("centerColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("glowColor", ControlKind.Color, "00000000", 0.0f, 48.0f, demo = false),
        Control("centerRadius", ControlKind.Dp, "9", 0.0f, 48.0f, demo = false),
        Control("strokeWidth", ControlKind.Dp, "2", 0.0f, 48.0f, demo = false),
        Control("orbitSpacing", ControlKind.Dp, "12", 0.0f, 48.0f, demo = false),
        Control("centerBorderWidth", ControlKind.Dp, "0", 0.0f, 48.0f, demo = false),
        Control("glowRadius", ControlKind.Dp, "8", 0.0f, 48.0f, demo = false),
        Control("orbitingObjectRadius", ControlKind.Dp, "4", 0.0f, 48.0f, demo = false),
        Control("orbitCount", ControlKind.Int, "3", 1.0f, 5.0f, demo = false),
        Control("orbitTilt", ControlKind.Float, "0.55", 0.1f, 1.0f, demo = false),
        Control("rotation", ControlKind.Float, "0", -360.0f, 360.0f, demo = false),
        Control("orbitAlpha", ControlKind.Float, "0.8", 0.0f, 1.0f, demo = false),
        Control("orbitStyle", ControlKind.Choice, "SOLID", 0.0f, 48.0f, choices = listOf("SOLID", "DASHED", "DOTTED"), demo = false),
        Control("showCenter", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("centerBorderColor", ControlKind.Color, "00000000", 0.0f, 48.0f, demo = false),
        Control("showOrbitingObjects", ControlKind.Boolean, "false", 0.0f, 48.0f, demo = false),
        Control("orbitingObjectColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("animated", ControlKind.Boolean, "false", 0.0f, 48.0f, demo = false),
        Control("rotationDuration", ControlKind.Int, "8000", 100.0f, 10000.0f, demo = false),
        Control("reverseRotation", ControlKind.Boolean, "false", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosCircularProgressLoader", "Circular loader", "Loaders", "Style a rotating arc and optional circular track.", "Adjust speed, sweep angle and stroke width.", "loaders", listOf(
        Control("color", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("trackColor", ControlKind.Color, "1FFFFFFF", 0.0f, 48.0f, demo = false),
        Control("strokeWidth", ControlKind.Dp, "4", 0.0f, 48.0f, demo = false),
        Control("sweepAngle", ControlKind.Float, "100", 1.0f, 359.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "1000", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("showTrack", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosDotsLoader", "Dots loader", "Loaders", "Customize bouncing dots with configurable spacing and timing.", "Change dot count, bounce height and speed.", "loaders", listOf(
        Control("dotColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("dotCount", ControlKind.Int, "3", 1.0f, 12.0f, demo = false),
        Control("dotSize", ControlKind.Dp, "10", 0.0f, 48.0f, demo = false),
        Control("dotSpacing", ControlKind.Dp, "10", 0.0f, 48.0f, demo = false),
        Control("bounceHeight", ControlKind.Dp, "10", 0.0f, 48.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "600", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosMorphLoader", "Morph loader", "Loaders", "Morph between rounded silhouettes with fill or outline.", "Compare filled and outlined animation.", "loaders", listOf(
        Control("color", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("size", ControlKind.Dp, "64", 24.0f, 150.0f, demo = false),
        Control("strokeWidth", ControlKind.Dp, "4", 0.0f, 48.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "1800", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("filled", ControlKind.Boolean, "false", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosOrbitLoader", "Orbit loader", "Loaders", "Animate orbital arcs and dots with optional tracks.", "Change the number of orbits and rotation duration.", "loaders", listOf(
        Control("orbitColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("trackColor", ControlKind.Color, "1FFFFFFF", 0.0f, 48.0f, demo = false),
        Control("orbitCount", ControlKind.Int, "3", 1.0f, 5.0f, demo = false),
        Control("strokeWidth", ControlKind.Dp, "2", 0.0f, 48.0f, demo = false),
        Control("dotRadius", ControlKind.Dp, "3", 0.0f, 48.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "1800", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("showTrack", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosPulseLoader", "Pulse loader", "Loaders", "Create expanding rings around an optional center dot.", "Change pulse count, radii and animation speed.", "loaders", listOf(
        Control("pulseColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("centerColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("pulseCount", ControlKind.Int, "3", 1.0f, 5.0f, demo = false),
        Control("strokeWidth", ControlKind.Dp, "2", 0.0f, 48.0f, demo = false),
        Control("centerRadius", ControlKind.Dp, "5", 0.0f, 48.0f, demo = false),
        Control("maxRadius", ControlKind.Dp, "34", 10.0f, 80.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "1200", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("showCenterDot", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosWaveLoader", "Wave loader", "Loaders", "Shape an animated equalizer from bars.", "Adjust bar dimensions, spacing and rounding.", "loaders", listOf(
        Control("waveColor", ControlKind.Color, "FFB56BFF", 0.0f, 48.0f, demo = false),
        Control("barCount", ControlKind.Int, "5", 1.0f, 20.0f, demo = false),
        Control("barWidth", ControlKind.Dp, "5", 0.0f, 48.0f, demo = false),
        Control("barSpacing", ControlKind.Dp, "6", 0.0f, 48.0f, demo = false),
        Control("minBarHeight", ControlKind.Dp, "12", 1.0f, 40.0f, demo = false),
        Control("maxBarHeight", ControlKind.Dp, "48", 10.0f, 80.0f, demo = false),
        Control("cornerRadius", ControlKind.Dp, "4", 0.0f, 48.0f, demo = false),
        Control("animationDurationMillis", ControlKind.Int, "900", 100.0f, 4000.0f, demo = false),
        Control("isRunning", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosMountainBackground", "Mountain landscape", "Backgrounds", "Art direct a layered landscape with a moon and stars.", "Change each layer color and toggle the moon and stars.", "backgrounds", listOf(
        Control("backgroundColor", ControlKind.Color, "FF050008", 0.0f, 48.0f, demo = false),
        Control("moonColor", ControlKind.Color, "FFE0E0E0", 0.0f, 48.0f, demo = false),
        Control("moonGlowColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("starColor", ControlKind.Color, "FFFFFFFF", 0.0f, 48.0f, demo = false),
        Control("backMountainColor", ControlKind.Color, "FF0A000A", 0.0f, 48.0f, demo = false),
        Control("middleMountainColor", ControlKind.Color, "FF0B0611", 0.0f, 48.0f, demo = false),
        Control("frontMountainColor", ControlKind.Color, "FF050008", 0.0f, 48.0f, demo = false),
        Control("moonGlowAlpha", ControlKind.Float, "0.30", 0.0f, 1.0f, demo = false),
        Control("skyGlowAlpha", ControlKind.Float, "0.30", 0.0f, 1.0f, demo = false),
        Control("showMoon", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("showStars", ControlKind.Boolean, "true", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosRoundBackground", "Round background", "Backgrounds", "Tune a circular radial surface and its border.", "Try a bright highlight against a darker background.", "backgrounds", listOf(
        Control("highlightColor", ControlKind.Color, "FF141419", 0.0f, 48.0f, demo = false),
        Control("backgroundColor", ControlKind.Color, "FF0A0A0A", 0.0f, 48.0f, demo = false),
        Control("borderColor", ControlKind.Color, "66FFFFFF", 0.0f, 48.0f, demo = false),
        Control("borderWidth", ControlKind.Dp, "2", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
    ComponentSpec("KosmosBottomWaveBackground", "Wave background", "Backgrounds", "Compose layered wave strokes with glow and highlights.", "Each wave has its own color and stroke controls.", "backgrounds", listOf(
        Control("firstWaveColor", ControlKind.Color, "FF323233", 0.0f, 48.0f, demo = false),
        Control("secondWaveColor", ControlKind.Color, "FF85858F", 0.0f, 48.0f, demo = false),
        Control("thirdWaveColor", ControlKind.Color, "FF161617", 0.0f, 48.0f, demo = false),
        Control("glowAlpha", ControlKind.Float, "0.05", 0.0f, 1.0f, demo = false),
        Control("waveAlpha", ControlKind.Float, "0.22", 0.0f, 1.0f, demo = false),
        Control("highlightAlpha", ControlKind.Float, "0.9", 0.0f, 1.0f, demo = false),
        Control("glowWidth", ControlKind.Dp, "20", 0.0f, 48.0f, demo = false),
        Control("waveWidth", ControlKind.Dp, "7", 0.0f, 48.0f, demo = false),
        Control("highlightWidth", ControlKind.Dp, "1.5", 0.0f, 48.0f, demo = false),
        Control("demoText", ControlKind.Text, "Explore your style", 0.0f, 1.0f, demo = true),
        Control("stageColor", ControlKind.Color, "FF181923", 0.0f, 1.0f, demo = true),
        Control("contentColor", ControlKind.Color, "FFF3F1FA", 0.0f, 1.0f, demo = true),
    )),
)

@Composable
internal fun ComponentDemo(spec: ComponentSpec, s: ComponentSettings, replay: Int, report: (String) -> Unit) {
    val text = s.string("demoText")
    val ink = s.color("contentColor")
    key(spec.id, replay) {
        when (spec.id) {
            "KosmosGlassCard" -> {
                KosmosGlassCard(
                    shape = RoundedCornerShape(s.dp("shape")),
                    backgroundColor = s.color("backgroundColor"),
                    backgroundAlpha = s.number("backgroundAlpha"),
                    borderColor = s.color("borderColor"),
                    borderWidth = s.dp("borderWidth"),
                    contentPadding = PaddingValues(s.dp("contentPadding"))
                ) { Text(text, color = ink) }
            }
            "KosmosGradientButton" -> {
                KosmosGradientButton(
                    enabled = s.boolean("enabled"),
                    gradientColors = listOf(s.color("gradient1"), s.color("gradient2"), s.color("gradient3")),
                    shape = RoundedCornerShape(s.dp("shape")),
                    borderColor = s.color("borderColor"),
                    borderWidth = s.dp("borderWidth"),
                    contentPadding = PaddingValues(s.dp("contentPadding")),
                    onClick = { report("Button clicked") }
                ) { Text(if (s.boolean("customSlot")) "+  $text" else text, color = ink) }
            }
            "KosmosGradientText" -> {
                KosmosGradientText(
                    text = s.string("text"),
                    gradientColors = listOf(s.color("gradient1"), s.color("gradient2"), s.color("gradient3")),
                    fontSize = s.sp("fontSize"),
                    fontWeight = when (s.string("fontWeight")) { "Normal" -> FontWeight.Normal; "Medium" -> FontWeight.Medium; "Black" -> FontWeight.Black; else -> FontWeight.Bold },
                    fontFamily = when (s.string("fontFamily")) { "Serif" -> FontFamily.Serif; "Monospace" -> FontFamily.Monospace; "SansSerif" -> FontFamily.SansSerif; else -> FontFamily.Default },
                    fontStyle = if (s.string("fontStyle") == "Italic") FontStyle.Italic else FontStyle.Normal,
                    textAlign = when (s.string("textAlign")) { "Center" -> TextAlign.Center; "End" -> TextAlign.End; else -> TextAlign.Start },
                    letterSpacing = s.sp("letterSpacing"),
                    lineHeight = s.sp("lineHeight"),
                    maxLines = s.integer("maxLines"),
                    overflow = when (s.string("overflow")) { "Ellipsis" -> TextOverflow.Ellipsis; "Visible" -> TextOverflow.Visible; else -> TextOverflow.Clip },
                    softWrap = s.boolean("softWrap")
                )
            }
            "KosmosNeumorphicCard" -> {
                KosmosNeumorphicCard(
                    shape = RoundedCornerShape(s.dp("shape")),
                    style = KosmosNeumorphicStyle.valueOf(s.string("style")),
                    surfaceColor = s.color("surfaceColor"),
                    lightShadowColor = s.color("lightShadowColor"),
                    darkShadowColor = s.color("darkShadowColor"),
                    shadowRadius = s.dp("shadowRadius"),
                    shadowDistance = s.dp("shadowDistance"),
                    contentPadding = PaddingValues(s.dp("contentPadding")),
                    enabled = s.boolean("enabled"),
                    onClick = { report("Card clicked") }
                ) { Text(text, color = ink) }
            }
            "KosmosSearchBar" -> {
                var query by remember { mutableStateOf("Kosmos") }
                KosmosSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = s.string("placeholder"),
                    enabled = s.boolean("enabled"),
                    readOnly = s.boolean("readOnly"),
                    shape = RoundedCornerShape(s.dp("shape")),
                    backgroundColor = s.color("backgroundColor"),
                    textColor = s.color("textColor"),
                    placeholderColor = s.color("placeholderColor"),
                    borderColor = s.color("borderColor"),
                    borderWidth = s.dp("borderWidth"),
                    cursorColor = s.color("cursorColor"),
                    contentPadding = PaddingValues(s.dp("contentPadding")),
                    showClearButton = s.boolean("showClearButton"),
                    clearButtonColor = s.color("clearButtonColor"),
                    value = query,
                    onValueChange = { query = it; report("Query: $it") },
                    leadingIcon = if (s.boolean("customSlot")) { { Text("⌕", color = ink) } } else null,
                    trailingIcon = if (s.boolean("customSlot")) { { Text("Filter", color = ink) } } else null,
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            "KosmosCardStack" -> {
                val cards = remember(s.integer("itemCount")) { (1..s.integer("itemCount")).map { "Card $it" } }
                KosmosCardStack(
                    modifier = Modifier.fillMaxWidth(),
                    cardHeight = s.dp("cardHeight"),
                    visibleCardCount = s.integer("visibleCardCount"),
                    stackOffset = s.dp("stackOffset"),
                    scaleStep = s.number("scaleStep"),
                    swipeThreshold = s.number("swipeThreshold"),
                    cardShape = RoundedCornerShape(s.dp("cardShape")),
                    cardColor = s.color("cardColor"),
                    cardElevation = s.dp("cardElevation"),
                    enabled = s.boolean("enabled"),
                    cards = cards,
                    onCardSwiped = { item, direction -> report("$item swiped $direction") }
                ) { item -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("$item · $text", color = ink) } }
            }
            "KosmosDraggableSheet" -> {
                var expanded by remember { mutableStateOf(false) }
                KosmosDraggableSheet(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    peekHeight = s.dp("peekHeight"),
                    sheetMaxHeight = s.dp("sheetMaxHeight"),
                    sheetShape = RoundedCornerShape(s.dp("sheetShape")),
                    sheetColor = s.color("sheetColor"),
                    scrimColor = s.color("scrimColor"),
                    scrimEnabled = s.boolean("scrimEnabled"),
                    dismissOnScrimClick = s.boolean("dismissOnScrimClick"),
                    dragHandle = s.boolean("dragHandle"),
                    positionalThreshold = s.number("positionalThreshold"),
                    velocityThreshold = s.dp("velocityThreshold"),
                    enabled = s.boolean("enabled"),
                    expanded = expanded,
                    onExpandedChange = { expanded = it; report(if (it) "Sheet expanded" else "Sheet collapsed") },
                    dragHandleContent = if (s.boolean("customSlot")) { { Text("•••", Modifier.padding(8.dp), color = ink) } } else null,
                    content = { Button(onClick = { expanded = !expanded }) { Text("Open sheet") } },
                    sheetContent = { Text(text, Modifier.padding(16.dp), color = ink); Text("Drag to explore", Modifier.padding(16.dp), color = ink) }
                )
            }
            "KosmosRadialMenu" -> {
                var expanded by remember { mutableStateOf(true) }
                val actions = (1..s.integer("itemCount")).map { index -> KosmosRadialAction("Action $index", { Text("$index", color = ink) }, { report("Action $index selected") }) }
                KosmosRadialMenu(
                    layout = KosmosRadialLayout.valueOf(s.string("layout")),
                    mainButtonColor = s.color("mainButtonColor"),
                    actionButtonColor = s.color("actionButtonColor"),
                    backgroundColor = s.color("backgroundColor"),
                    backgroundShape = RoundedCornerShape(s.dp("backgroundShape")),
                    mainButtonSize = s.dp("mainButtonSize"),
                    actionButtonSize = s.dp("actionButtonSize"),
                    radius = s.dp("radius"),
                    startAngle = s.number("startAngle"),
                    endAngle = s.number("endAngle"),
                    showActionShadow = s.boolean("showActionShadow"),
                    showMainShadow = s.boolean("showMainShadow"),
                    enabled = s.boolean("enabled"),
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    actions = actions,
                    menuIcon = { Text(if (s.boolean("customSlot")) "◎" else "+", color = ink) },
                    closeIcon = { Text("×", color = ink) }
                )
            }
            "KosmosSwipeAction" -> {
                var side by remember { mutableStateOf<KosmosSwipeSide?>(null) }
                val actions = (1..s.integer("itemCount")).map { index -> KosmosSwipeActionItem(
                    label = if (index == 1) "Save" else "Action $index",
                    icon = { Text(if (index == 1) "+" else "×", color = ink) },
                    onClick = { report("Action $index selected") },
                    backgroundColor = s.color("actionColor"), contentColor = ink
                ) }
                KosmosSwipeAction(
                    actionWidth = s.dp("actionWidth"),
                    actionSpacing = s.dp("actionSpacing"),
                    actionCornerRadius = s.dp("actionCornerRadius"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    revealThreshold = s.number("revealThreshold"),
                    velocityThreshold = s.dp("velocityThreshold"),
                    enabled = s.boolean("enabled"),
                    contentShape = RoundedCornerShape(s.dp("contentShape")),
                    revealedSide = side,
                    onRevealedSideChange = { side = it },
                    startActions = actions,
                    endActions = actions.reversed()
                ) { Box(Modifier.fillMaxWidth().height(100.dp).background(s.color("stageColor")).padding(16.dp)) { Text(text, color = ink) } }
            }
            "KosmosToggle" -> {
                var checked by remember { mutableStateOf(false) }
                KosmosToggle(
                    enabled = s.boolean("enabled"),
                    surfaceColor = s.color("surfaceColor"),
                    activeThumbColor = s.color("activeThumbColor"),
                    inactiveThumbColor = s.color("inactiveThumbColor"),
                    lightShadowColor = s.color("lightShadowColor"),
                    darkShadowColor = s.color("darkShadowColor"),
                    disabledThumbColor = s.color("disabledThumbColor"),
                    trackWidth = s.dp("trackWidth"),
                    trackHeight = s.dp("trackHeight"),
                    thumbSize = s.dp("thumbSize"),
                    animationEnabled = s.boolean("animationEnabled"),
                    checked = checked,
                    onCheckedChange = { checked = it; report(if (it) "Switch on" else "Switch off") }
                )
            }
            "KosmosBlob" -> {
                KosmosBlob(
                    modifier = Modifier.size(220.dp),
                    color = s.color("color"),
                    pointCount = s.integer("pointCount"),
                    irregularity = s.number("irregularity")
                )
            }
            "KosmosLiquidShape" -> {
                KosmosLiquidShape(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    color = s.color("color"),
                    curveHeight = s.number("curveHeight"),
                    curveDepth = s.number("curveDepth")
                )
            }
            "KosmosOrbit" -> {
                KosmosOrbit(
                    modifier = Modifier.size(220.dp),
                    orbitColor = s.color("orbitColor"),
                    centerColor = s.color("centerColor"),
                    glowColor = s.color("glowColor"),
                    centerRadius = s.dp("centerRadius"),
                    strokeWidth = s.dp("strokeWidth"),
                    orbitSpacing = s.dp("orbitSpacing"),
                    centerBorderWidth = s.dp("centerBorderWidth"),
                    glowRadius = s.dp("glowRadius"),
                    orbitingObjectRadius = s.dp("orbitingObjectRadius"),
                    orbitCount = s.integer("orbitCount"),
                    orbitTilt = s.number("orbitTilt"),
                    rotation = s.number("rotation"),
                    orbitAlpha = s.number("orbitAlpha"),
                    orbitStyle = OrbitStyle.valueOf(s.string("orbitStyle")),
                    showCenter = s.boolean("showCenter"),
                    centerBorderColor = s.color("centerBorderColor"),
                    showOrbitingObjects = s.boolean("showOrbitingObjects"),
                    orbitingObjectColor = s.color("orbitingObjectColor"),
                    animated = s.boolean("animated"),
                    rotationDuration = s.integer("rotationDuration"),
                    reverseRotation = s.boolean("reverseRotation")
                )
            }
            "KosmosCircularProgressLoader" -> {
                KosmosCircularProgressLoader(
                    modifier = Modifier.size(140.dp),
                    color = s.color("color"),
                    trackColor = s.color("trackColor"),
                    strokeWidth = s.dp("strokeWidth"),
                    sweepAngle = s.number("sweepAngle"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning"),
                    showTrack = s.boolean("showTrack")
                )
            }
            "KosmosDotsLoader" -> {
                KosmosDotsLoader(
                    modifier = Modifier.size(250.dp, 140.dp),
                    dotColor = s.color("dotColor"),
                    dotCount = s.integer("dotCount"),
                    dotSize = s.dp("dotSize"),
                    dotSpacing = s.dp("dotSpacing"),
                    bounceHeight = s.dp("bounceHeight"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning")
                )
            }
            "KosmosMorphLoader" -> {
                KosmosMorphLoader(
                    color = s.color("color"),
                    size = s.dp("size"),
                    strokeWidth = s.dp("strokeWidth"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning"),
                    filled = s.boolean("filled")
                )
            }
            "KosmosOrbitLoader" -> {
                KosmosOrbitLoader(
                    modifier = Modifier.size(140.dp),
                    orbitColor = s.color("orbitColor"),
                    trackColor = s.color("trackColor"),
                    orbitCount = s.integer("orbitCount"),
                    strokeWidth = s.dp("strokeWidth"),
                    dotRadius = s.dp("dotRadius"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning"),
                    showTrack = s.boolean("showTrack")
                )
            }
            "KosmosPulseLoader" -> {
                KosmosPulseLoader(
                    modifier = Modifier.size(140.dp),
                    pulseColor = s.color("pulseColor"),
                    centerColor = s.color("centerColor"),
                    pulseCount = s.integer("pulseCount"),
                    strokeWidth = s.dp("strokeWidth"),
                    centerRadius = s.dp("centerRadius"),
                    maxRadius = s.dp("maxRadius"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning"),
                    showCenterDot = s.boolean("showCenterDot")
                )
            }
            "KosmosWaveLoader" -> {
                KosmosWaveLoader(
                    modifier = Modifier.size(250.dp, 140.dp),
                    waveColor = s.color("waveColor"),
                    barCount = s.integer("barCount"),
                    barWidth = s.dp("barWidth"),
                    barSpacing = s.dp("barSpacing"),
                    minBarHeight = s.dp("minBarHeight"),
                    maxBarHeight = s.dp("maxBarHeight"),
                    cornerRadius = s.dp("cornerRadius"),
                    animationDurationMillis = s.integer("animationDurationMillis"),
                    isRunning = s.boolean("isRunning")
                )
            }
            "KosmosMountainBackground" -> {
                KosmosMountainBackground(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    backgroundColor = s.color("backgroundColor"),
                    moonColor = s.color("moonColor"),
                    moonGlowColor = s.color("moonGlowColor"),
                    starColor = s.color("starColor"),
                    backMountainColor = s.color("backMountainColor"),
                    middleMountainColor = s.color("middleMountainColor"),
                    frontMountainColor = s.color("frontMountainColor"),
                    moonGlowAlpha = s.number("moonGlowAlpha"),
                    skyGlowAlpha = s.number("skyGlowAlpha"),
                    showMoon = s.boolean("showMoon"),
                    showStars = s.boolean("showStars")
                )
            }
            "KosmosRoundBackground" -> {
                KosmosRoundBackground(
                    modifier = Modifier.size(220.dp),
                    highlightColor = s.color("highlightColor"),
                    backgroundColor = s.color("backgroundColor"),
                    borderColor = s.color("borderColor"),
                    borderWidth = s.dp("borderWidth")
                )
            }
            "KosmosBottomWaveBackground" -> {
                KosmosBottomWaveBackground(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    firstWaveColor = s.color("firstWaveColor"),
                    secondWaveColor = s.color("secondWaveColor"),
                    thirdWaveColor = s.color("thirdWaveColor"),
                    glowAlpha = s.number("glowAlpha"),
                    waveAlpha = s.number("waveAlpha"),
                    highlightAlpha = s.number("highlightAlpha"),
                    glowWidth = s.dp("glowWidth"),
                    waveWidth = s.dp("waveWidth"),
                    highlightWidth = s.dp("highlightWidth")
                )
            }
        }
    }
}
