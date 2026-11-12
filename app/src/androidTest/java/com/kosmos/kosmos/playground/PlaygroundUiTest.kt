package com.kosmos.kosmos.playground

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

class PlaygroundUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun backReturnsToFilteredCatalogAtTheSameScrollPosition() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { KosmosPlayground() }
        compose.onNode(hasSetTextAction()).performTextInput("loader")
        compose.onNodeWithText("Loaders").performScrollTo().performClick()
        val catalog = compose.onNodeWithTag("componentCatalog")
        catalog.performScrollToNode(hasText("Wave loader"))
        val originalScroll = catalog.fetchSemanticsNode().config[SemanticsProperties.VerticalScrollAxisRange].value()
        // Wave loaders animate continuously; control the test clock while the editor is open.
        compose.mainClock.autoAdvance = false
        compose.onNodeWithText("Wave loader").performClick()
        compose.mainClock.advanceTimeBy(32)
        restoration.emulateSavedInstanceStateRestore()
        compose.mainClock.advanceTimeBy(32)
        compose.onNodeWithText("‹ Back").performClick()
        compose.mainClock.autoAdvance = true
        catalog.assertExists()
        assertEquals(originalScroll, catalog.fetchSemanticsNode().config[SemanticsProperties.VerticalScrollAxisRange].value(), 0.001f)
        compose.onNodeWithText("Wave loader").assertIsDisplayed()
        catalog.performScrollToIndex(0)
        compose.onNode(hasSetTextAction()).assertTextEquals("loader")
        compose.onNodeWithText("Loaders").performScrollTo().assertIsSelected()
        // Reopen a loader and use Android's system Back as well as the toolbar Back.
        catalog.performScrollToNode(hasText("Wave loader"))
        compose.mainClock.autoAdvance = false
        compose.onNodeWithText("Wave loader").performClick()
        compose.mainClock.advanceTimeBy(32)
        androidx.test.espresso.Espresso.pressBack()
        compose.mainClock.autoAdvance = true
        compose.onNodeWithText("Wave loader").assertIsDisplayed()
    }

    @Test fun browseCustomizeAndExportNeumorphicStyle() {
        compose.setContent { KosmosPlayground() }
        compose.onNode(hasSetTextAction()).performTextInput("Neumorphic")
        compose.onNodeWithText("Neumorphic card").performClick()
        compose.onNodeWithText("Midnight").performClick()
        compose.onNodeWithText("Kotlin code").performClick()
        compose.onNodeWithText("surfaceColor = Color(0xFF252833)", substring = true).assertExists()
        compose.onNodeWithText("Copy code").performClick()
        compose.onNodeWithText("Copied ✓").assertExists()
        compose.onNodeWithText("Reset").performClick()
        compose.onNodeWithText("surfaceColor = Color(0xFFE0E5EC)", substring = true).assertExists()
    }

    @Test fun selectedComponentAndConfigurationSurviveRestoration() {
        val restoration = StateRestorationTester(compose)
        restoration.setContent { KosmosPlayground() }
        compose.onNode(hasSetTextAction()).performTextInput("Neumorphic")
        compose.onNodeWithText("Neumorphic card").performClick()
        compose.onNodeWithText("Lavender").performClick()
        restoration.emulateSavedInstanceStateRestore()
        compose.onNodeWithText("Neumorphic card").assertExists()
        compose.onNodeWithText("Kotlin code").performClick()
        compose.onNodeWithText("surfaceColor = Color(0xFFE2DDF3)", substring = true).assertExists()
    }

    @Test fun everyComponentRendersWithDefaultAndBoundarySettings() {
        // Freeze infinite loader animations; advance deterministic frames explicitly.
        compose.mainClock.autoAdvance = false
        var selected by mutableStateOf(componentCatalog.first())
        var version by mutableIntStateOf(0)
        val store = PlaygroundStore()
        compose.setContent {
            MaterialTheme {
                Box(Modifier.size(360.dp)) {
                    ComponentDemo(selected, ComponentSettings(selected, store), version) {}
                }
            }
        }
        componentCatalog.forEach { spec ->
            compose.runOnIdle { selected = spec; version++ }
            compose.mainClock.advanceTimeBy(32)
            compose.waitForIdle()
            compose.runOnIdle {
                val settings = ComponentSettings(spec, store)
                spec.controls.forEach { control ->
                    settings.set(control.key, when (control.kind) {
                        ControlKind.Color -> "FF8B5CF6"
                        ControlKind.Boolean -> (!control.initial.toBoolean()).toString()
                        ControlKind.Choice -> control.choices.last()
                        ControlKind.Text -> "Custom text"
                        else -> control.maximum.literal()
                    })
                }
                version++
            }
            compose.mainClock.advanceTimeBy(32)
            compose.waitForIdle()
        }
    }
}
