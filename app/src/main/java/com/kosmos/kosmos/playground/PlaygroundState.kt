package com.kosmos.kosmos.playground

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

internal enum class ControlKind { Color, Dp, Float, Int, Sp, Boolean, Text, Choice }

internal data class Control(
    val key: String,
    val kind: ControlKind,
    val initial: String,
    val minimum: Float = 0f,
    val maximum: Float = 1f,
    val choices: List<String> = emptyList(),
    val demo: Boolean = false
)

internal data class ComponentSpec(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val interaction: String,
    val packageName: String,
    val controls: List<Control>
)

/** Only string values are saved, keeping configuration changes and Android restoration predictable. */
internal class PlaygroundStore(initial: Map<String, String> = emptyMap()) {
    var values by mutableStateOf(initial)
        private set

    fun get(key: String): String? = values[key]
    fun put(key: String, value: String) { values = values + (key to value) }
    fun reset(id: String) { values = values.filterKeys { !it.startsWith("$id/") } }

    companion object {
        val Saver = listSaver<PlaygroundStore, String>(
            save = { it.values.flatMap { (key, value) -> listOf(key, value) } },
            restore = { PlaygroundStore(it.chunked(2).associate { pair -> pair[0] to pair[1] }) }
        )
    }
}

internal class ComponentSettings(val spec: ComponentSpec, private val store: PlaygroundStore) {
    fun string(key: String): String = store.get("${spec.id}/$key")
        ?: spec.controls.first { it.key == key }.initial
    fun set(key: String, value: String) = store.put("${spec.id}/$key", value)
    fun number(key: String) = string(key).toFloat()
    fun integer(key: String) = number(key).toInt()
    fun boolean(key: String) = string(key).toBoolean()
    fun color(key: String) = Color(string(key).toLong(16))
    fun dp(key: String) = number(key).dp
    fun sp(key: String) = number(key).sp
    fun reset() = store.reset(spec.id)
}

internal fun Color.hex(): String = String.format(Locale.US, "%08X", toArgb())
internal fun Float.literal(): String = String.format(Locale.US, "%.2f", this).trimEnd('0').trimEnd('.')
internal fun String.label(): String = replace(Regex("([a-z])([A-Z0-9])"), "$1 $2")
    .replaceFirstChar { it.uppercase() }

/** Quotes arbitrary user content as a Kotlin string, including dollar signs and control characters. */
internal fun kotlinString(value: String): String = buildString {
    append('"')
    for (character in value) append(when (character) {
        '\\' -> "\\\\"
        '"' -> "\\\""
        '$' -> "\\$"
        '\n' -> "\\n"
        '\r' -> "\\r"
        '\t' -> "\\t"
        else -> if (character.code < 32) "\\u%04x".format(character.code) else character.toString()
    })
    append('"')
}
