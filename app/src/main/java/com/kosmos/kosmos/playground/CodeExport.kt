package com.kosmos.kosmos.playground

/** Exports the current argument values, not a preset or a screenshot. */
internal fun exportCode(spec: ComponentSpec, s: ComponentSettings): String {
    val types = parameterTypes.getValue(spec.id)
    val args = mutableListOf<String>()
    val modifier = when (spec.id) {
        "KosmosLiquidShape", "KosmosBottomWaveBackground", "KosmosMountainBackground" -> "Modifier.fillMaxWidth().height(220.dp)"
        "KosmosDraggableSheet" -> "Modifier.fillMaxWidth().height(300.dp)"
        "KosmosCardStack", "KosmosSearchBar" -> "Modifier.fillMaxWidth()"
        "KosmosBlob", "KosmosOrbit", "KosmosRoundBackground" -> "Modifier.size(220.dp)"
        "KosmosDotsLoader", "KosmosWaveLoader" -> "Modifier.size(250.dp, 140.dp)"
        else -> if (spec.id.endsWith("Loader") && spec.id != "KosmosMorphLoader") "Modifier.size(140.dp)" else null
    }
    modifier?.let { args += "modifier = $it" }
    var gradientAdded = false
    for ((key, type) in types) {
        if (key.startsWith("gradient")) {
            if (!gradientAdded) args += "gradientColors = listOf(${(1..3).joinToString { "Color(0x${s.string("gradient$it")})" }})"
            gradientAdded = true
            continue
        }
        val value = when (type) {
            "Color" -> "Color(0x${s.string(key)})"
            "Shape" -> "RoundedCornerShape(${s.number(key).literal()}.dp)"
            "PaddingValues" -> "PaddingValues(${s.number(key).literal()}.dp)"
            "Dp" -> "${s.number(key).literal()}.dp"
            "TextUnit" -> "${s.number(key).literal()}.sp"
            "Float" -> "${s.number(key).literal()}f"
            "Int" -> s.integer(key).toString()
            "Boolean" -> s.boolean(key).toString()
            "String" -> kotlinString(s.string(key))
            "TextAlign?" -> "TextAlign.${s.string(key)}"
            "FontFamily?" -> "FontFamily.${s.string(key)}"
            "FontStyle?" -> "FontStyle.${s.string(key)}"
            else -> "$type.${s.string(key)}"
        }
        args += "$key = $value"
    }
    val sample = kotlinString(s.string("demoText"))
    val ink = "Color(0x${s.string("contentColor")})"
    val stage = "Color(0x${s.string("stageColor")})"
    val states = mutableListOf<String>()
    var content = ""
    when (spec.id) {
        "KosmosNeumorphicCard" -> {
            args += "onClick = { status = \"Card clicked\" }"
            content = " { Text($sample, color = $ink) }"
        }
        "KosmosGlassCard" -> content = " { Text($sample, color = $ink) }"
        "KosmosGradientButton" -> {
            args += "onClick = { status = \"Button clicked\" }"
            content = " { Text(${kotlinString((if (s.boolean("customSlot")) "+  " else "") + s.string("demoText"))}, color = $ink) }"
        }
        "KosmosSearchBar" -> {
            states += "var query by remember { mutableStateOf(\"Kosmos\") }"
            args += "value = query"
            args += "onValueChange = { query = it; status = it }"
            args += "textStyle = TextStyle(fontSize = 16.sp)"
            if (s.boolean("customSlot")) {
                args += "leadingIcon = { Text(\"⌕\", color = $ink) }"
                args += "trailingIcon = { Text(\"Filter\", color = $ink) }"
            }
        }
        "KosmosToggle" -> {
            states += "var checked by remember { mutableStateOf(false) }"
            args += "checked = checked"
            args += "onCheckedChange = { checked = it; status = if (it) \"Switch on\" else \"Switch off\" }"
        }
        "KosmosCardStack" -> {
            states += "val cards = remember { (1..${s.integer("itemCount")}).map { \"Card \$it\" } }"
            args += "cards = cards"
            args += "onCardSwiped = { item, direction -> status = \"\$item swiped \$direction\" }"
            content = " { item -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(\"\$item · \" + $sample, color = $ink) } }"
        }
        "KosmosSwipeAction" -> {
            states += "var side by remember { mutableStateOf<KosmosSwipeSide?>(null) }"
            states += """val actions = (1..${s.integer("itemCount")}).map { index ->
    KosmosSwipeActionItem(
        label = if (index == 1) "Save" else "Action ${'$'}index",
        icon = { Text(if (index == 1) "+" else "×", color = $ink) },
        onClick = { status = "Action ${'$'}index selected" },
        backgroundColor = Color(0x${s.string("actionColor")}), contentColor = $ink
    )
}"""
            args += listOf("revealedSide = side", "onRevealedSideChange = { side = it }", "startActions = actions", "endActions = actions.reversed()")
            content = " { Box(Modifier.fillMaxWidth().height(100.dp).background($stage).padding(16.dp)) { Text($sample, color = $ink) } }"
        }
        "KosmosDraggableSheet" -> {
            states += "var expanded by remember { mutableStateOf(false) }"
            args += listOf("expanded = expanded", "onExpandedChange = { expanded = it }",
                "content = { Button(onClick = { expanded = !expanded }) { Text(\"Open sheet\") } }",
                "sheetContent = { Text($sample, Modifier.padding(16.dp), color = $ink); Text(\"Drag to explore\", Modifier.padding(16.dp), color = $ink) }")
            if (s.boolean("customSlot")) args += "dragHandleContent = { Text(\"•••\", Modifier.padding(8.dp), color = $ink) }"
        }
        "KosmosRadialMenu" -> {
            states += "var expanded by remember { mutableStateOf(true) }"
            states += """val actions = (1..${s.integer("itemCount")}).map { index ->
    KosmosRadialAction("Action ${'$'}index", { Text("${'$'}index", color = $ink) }, { status = "Action ${'$'}index selected" })
}"""
            args += listOf("expanded = expanded", "onExpandedChange = { expanded = it }", "actions = actions",
                "menuIcon = { Text(${kotlinString(if (s.boolean("customSlot")) "◎" else "+")}, color = $ink) }", "closeIcon = { Text(\"×\", color = $ink) }")
        }
    }
    val call = spec.id + "(\n" + args.joinToString(",\n") { "    $it" } + "\n)" + content
    val body = buildString {
        appendLine("var status by remember { mutableStateOf(\"Try the component\") }")
        states.forEach { appendLine(it) }
        appendLine("Column {")
        appendLine("    Box(Modifier.fillMaxWidth().height(360.dp).background($stage).padding(20.dp), contentAlignment = Alignment.Center) {")
        appendLine(call.prependIndent("        "))
        appendLine("    }")
        appendLine("    Text(status)")
        append("}")
    }
    return """import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.kosmos.components.${spec.packageName}.*

@Composable
fun My${spec.id.removePrefix("Kosmos")}() {
${body.prependIndent("    ")}
}
"""
}
