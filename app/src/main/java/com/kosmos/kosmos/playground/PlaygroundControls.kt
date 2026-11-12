package com.kosmos.kosmos.playground

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SettingsControls(spec: ComponentSpec, settings: ComponentSettings) {
    var colorKey by rememberSaveable(spec.id) { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("MAKE IT YOURS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        Text("Every change updates the component and Kotlin code immediately.", style = MaterialTheme.typography.bodySmall)
        if (spec.id == "KosmosNeumorphicCard") {
            Text("Starting points", style = MaterialTheme.typography.titleSmall)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Cloud", "Midnight", "Lavender").forEach { preset ->
                    OutlinedButton(onClick = {
                        val palette = when (preset) {
                            "Midnight" -> listOf("FF252833", "FF424857", "FF10121A", "FFF2F0FA")
                            "Lavender" -> listOf("FFE2DDF3", "FFFFFFFF", "FFB6ACD1", "FF3E3457")
                            else -> listOf("FFE0E5EC", "FFFFFFFF", "FFA3B1C6", "FF313448")
                        }
                        listOf("surfaceColor", "lightShadowColor", "darkShadowColor", "contentColor").zip(palette).forEach { (key, value) -> settings.set(key, value) }
                        settings.set("stageColor", palette[0])
                    }) { Text(preset) }
                }
            }
        }
        listOf(false, true).forEach { demo ->
            HorizontalDivider()
            Text(if (demo) "Demo content & backdrop" else "Component arguments", style = MaterialTheme.typography.titleMedium)
            if (demo) Text("These settings customize the surrounding example. The exported code includes them separately.", style = MaterialTheme.typography.bodySmall)
            spec.controls.filter { it.demo == demo }.forEach { control ->
                val value = settings.string(control.key)
                when (control.kind) {
                    ControlKind.Color -> {
                        Surface(onClick = { colorKey = control.key }, shape = MaterialTheme.shapes.medium, tonalElevation = 2.dp) {
                            Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(Modifier.size(32.dp).clip(CircleShape).background(settings.color(control.key)).border(1.dp, Color.Gray, CircleShape))
                                Column(Modifier.weight(1f)) {
                                    Text(control.key.label(), style = MaterialTheme.typography.bodyMedium)
                                    Text("#$value", style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace)
                                }
                                Text("Edit", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                    ControlKind.Boolean -> Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(control.key.label(), Modifier.weight(1f))
                        Switch(checked = settings.boolean(control.key), onCheckedChange = { settings.set(control.key, it.toString()) })
                    }
                    ControlKind.Text -> OutlinedTextField(value = value, onValueChange = { settings.set(control.key, it) }, label = { Text(control.key.label()) }, modifier = Modifier.fillMaxWidth(), maxLines = 3)
                    ControlKind.Choice -> Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(control.key.label(), style = MaterialTheme.typography.labelLarge)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            control.choices.forEach { option -> FilterChip(selected = option == value, onClick = { settings.set(control.key, option) }, label = { Text(option) }) }
                        }
                    }
                    else -> Column {
                        val number = settings.number(control.key)
                        val unit = when (control.kind) { ControlKind.Dp -> " dp"; ControlKind.Sp -> " sp"; else -> "" }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(control.key.label(), style = MaterialTheme.typography.labelLarge)
                            Text(number.literal() + unit, fontFamily = FontFamily.Monospace, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                        }
                        Slider(
                            value = number.coerceIn(control.minimum, control.maximum),
                            onValueChange = { settings.set(control.key, (if (control.kind == ControlKind.Int) it.roundToInt().toFloat() else it).literal()) },
                            valueRange = control.minimum..control.maximum,
                            steps = if (control.kind == ControlKind.Int) (control.maximum - control.minimum).toInt() - 1 else 0
                        )
                    }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
    colorKey?.let { key ->
        ColorPicker(title = key.label(), color = settings.color(key), onChange = { settings.set(key, it.hex()) }, onDismiss = { colorKey = null })
    }
}

/** All channels including alpha are editable; incomplete hex never enters component state. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorPicker(title: String, color: Color, onChange: (Color) -> Unit, onDismiss: () -> Unit) {
    var hex by remember { mutableStateOf(color.hex()) }
    val valid = hex.matches(Regex("[0-9a-fA-F]{6}|[0-9a-fA-F]{8}"))
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.fillMaxWidth().height(56.dp).clip(MaterialTheme.shapes.medium).background(color).border(1.dp, Color.Gray, MaterialTheme.shapes.medium))
                OutlinedTextField(
                    value = hex, onValueChange = { input ->
                        hex = input.removePrefix("#").take(8)
                        if (hex.matches(Regex("[0-9a-fA-F]{6}|[0-9a-fA-F]{8}"))) onChange(Color((if (hex.length == 6) "FF$hex" else hex).toLong(16)))
                    }, label = { Text("Hex · RRGGBB or AARRGGBB") }, singleLine = true, isError = !valid,
                    supportingText = { Text(if (valid) "Changes apply live" else "Enter 6 or 8 hexadecimal digits") }
                )
                listOf("Red" to color.red, "Green" to color.green, "Blue" to color.blue, "Alpha" to color.alpha).forEachIndexed { index, (label, value) ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(label); Text((value * 255).roundToInt().toString()) }
                    Slider(value = value, onValueChange = { v -> val updated = when (index) {
                        0 -> color.copy(red = v); 1 -> color.copy(green = v); 2 -> color.copy(blue = v); else -> color.copy(alpha = v)
                    }; hex = updated.hex(); onChange(updated) })
                }
                FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf(0xFF8B5CF6, 0xFF22D3EE, 0xFFFB7185, 0xFF34D399, 0xFFFFB454, 0xFFE0E5EC, 0xFFFFFFFF, 0xFF17171D, 0x00000000).forEach { argb ->
                        val swatch = Color(argb)
                        Surface(onClick = { hex = swatch.hex(); onChange(swatch) }, modifier = Modifier.size(40.dp), shape = CircleShape, color = swatch, border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)) {}
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Done") } }
    )
}
