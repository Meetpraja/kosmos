package com.kosmos.kosmos.playground

import org.junit.Assert.*
import org.junit.Test
import java.io.File

class PlaygroundTest {
    @Test fun everyCatalogControlHasAValidDefault() {
        assertEquals(22, componentCatalog.size)
        assertEquals(22, componentCatalog.map { it.id }.distinct().size)
        componentCatalog.forEach { spec ->
            assertEquals(spec.id, spec.controls.size, spec.controls.map { it.key }.distinct().size)
            val settings = ComponentSettings(spec, PlaygroundStore())
            spec.controls.forEach { control ->
                when (control.kind) {
                    ControlKind.Color -> assertTrue(control.key, settings.string(control.key).matches(Regex("[A-Fa-f0-9]{8}")))
                    ControlKind.Boolean -> assertTrue(settings.string(control.key) in listOf("true", "false"))
                    ControlKind.Choice -> assertTrue(settings.string(control.key) in control.choices)
                    ControlKind.Text -> Unit
                    else -> assertTrue("${spec.id}.${control.key}", settings.number(control.key) in control.minimum..control.maximum)
                }
            }
        }
    }

    @Test fun exportUsesEditedColorsDimensionsAndStyle() {
        val spec = componentCatalog.first { it.id == "KosmosNeumorphicCard" }
        val settings = ComponentSettings(spec, PlaygroundStore())
        settings.set("surfaceColor", "FFAABBCC")
        settings.set("shadowRadius", "18.5")
        settings.set("style", "Pressed")
        val code = exportCode(spec, settings)
        assertTrue(code.contains("surfaceColor = Color(0xFFAABBCC)"))
        assertTrue(code.contains("shadowRadius = 18.5.dp"))
        assertTrue(code.contains("style = KosmosNeumorphicStyle.Pressed"))
    }

    @Test fun exportEscapesUserTextAsKotlinSource() {
        assertEquals("\"\\\"\\\\\\n\\t\\\$name\"", kotlinString("\"\\\n\t\$name"))
    }

    @Test fun resetOnlyClearsTheSelectedComponent() {
        val store = PlaygroundStore()
        val first = ComponentSettings(componentCatalog[0], store)
        val second = ComponentSettings(componentCatalog[1], store)
        first.set("stageColor", "FFAABBCC")
        second.set("stageColor", "FF123456")
        first.reset()
        assertEquals(first.spec.controls.first { it.key == "stageColor" }.initial, first.string("stageColor"))
        assertEquals("FF123456", second.string("stageColor"))
    }

    /** The generated files can also be compiled against the app to validate the copyable API examples. */
    @Test fun generateExportCompilationSamples() {
        val folder = File("build/generated/playgroundValidation").apply { mkdirs() }
        folder.listFiles()?.forEach { it.delete() }
        componentCatalog.forEach { spec ->
            val settings = ComponentSettings(spec, PlaygroundStore())
            folder.resolve("${spec.id}Default.kt").writeText("package playground.validation\n" + exportCode(spec, settings))
            spec.controls.forEach { control ->
                settings.set(control.key, when (control.kind) {
                    ControlKind.Color -> "AABBCCDD"
                    ControlKind.Boolean -> (!control.initial.toBoolean()).toString()
                    ControlKind.Choice -> control.choices.last()
                    ControlKind.Text -> "Custom \"quoted\" \$text\nexample"
                    else -> control.maximum.literal()
                })
            }
            folder.resolve("${spec.id}Custom.kt").writeText("package playground.validation\n" + exportCode(spec, settings).replace("fun My", "fun Custom"))
        }
        assertEquals(44, folder.listFiles()!!.size)
    }
}
