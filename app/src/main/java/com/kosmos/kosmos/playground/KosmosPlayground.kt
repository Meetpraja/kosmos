package com.kosmos.kosmos.playground

import android.content.ClipData
import android.content.ClipboardManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

private val StudioColors = darkColorScheme(
    primary = Color(0xFFC3AAFF), onPrimary = Color(0xFF271446),
    secondary = Color(0xFF78E3CF), background = Color(0xFF0D0E15),
    surface = Color(0xFF13151F), surfaceVariant = Color(0xFF222532),
    onSurface = Color(0xFFF2F0F8), onSurfaceVariant = Color(0xFFA7AABC),
    outline = Color(0xFF393D50)
)

@Composable
fun KosmosPlayground() {
    val store = rememberSaveable(saver = PlaygroundStore.Saver) { PlaygroundStore() }
    var selected by rememberSaveable { mutableStateOf<String?>(null) }
    val catalogState = rememberSaveableStateHolder()
    val spec = componentCatalog.find { it.id == selected }
    BackHandler(enabled = spec != null) { selected = null }
    MaterialTheme(colorScheme = StudioColors) {
        Scaffold(modifier = Modifier.imePadding(), containerColor = StudioColors.background, topBar = {
            Surface(color = StudioColors.background) {
                Row(Modifier.fillMaxWidth().statusBarsPadding().padding(horizontal = 20.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    if (spec != null) TextButton(onClick = { selected = null }) { Text("‹ Back") }
                    Column(Modifier.weight(1f)) {
                        Text("KOSMOS", letterSpacing = 4.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(if (spec == null) "COMPONENT STUDIO" else spec.category.uppercase(), letterSpacing = 2.sp, style = MaterialTheme.typography.labelSmall, color = StudioColors.secondary)
                    }
                    Text("22 components", style = MaterialTheme.typography.labelSmall, color = StudioColors.onSurfaceVariant)
                }
            }
        }) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                if (spec == null) catalogState.SaveableStateProvider("catalog") {
                    Catalog(onSelect = { selected = it.id })
                }
                else key(spec.id) { ComponentEditor(spec, ComponentSettings(spec, store)) }
            }
        }
    }
}

@Composable
private fun Catalog(onSelect: (ComponentSpec) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("All") }
    val listState = rememberLazyListState()
    val visible = componentCatalog.filter {
        (category == "All" || it.category == category) && "${it.title} ${it.category} ${it.description} ${it.id}".contains(query, ignoreCase = true)
    }
    LazyColumn(Modifier.fillMaxSize().testTag("componentCatalog"), state = listState, contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Column(Modifier.fillMaxWidth().background(Brush.linearGradient(listOf(Color(0xFF30214D), Color(0xFF152B32))), MaterialTheme.shapes.large).padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("YOUR NEXT UI STARTS HERE", color = StudioColors.secondary, style = MaterialTheme.typography.labelSmall, letterSpacing = 2.sp)
                Text("Find your style.\nMake it yours.", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                Text("Explore real components. Tune every detail. Take the Kotlin code into your app.", color = Color(0xFFCEC8DC))
            }
        }
        item { OutlinedTextField(value = query, onValueChange = { query = it }, placeholder = { Text("Search components, effects, interactions…") }, modifier = Modifier.fillMaxWidth(), singleLine = true, shape = MaterialTheme.shapes.large) }
        item {
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (listOf("All") + componentCatalog.map { it.category }.distinct()).forEach { item ->
                    FilterChip(selected = category == item, onClick = { category = item }, label = { Text(item) })
                }
            }
        }
        item { Text("${visible.size} COMPONENTS TO EXPLORE", style = MaterialTheme.typography.labelSmall, letterSpacing = 1.sp, color = StudioColors.onSurfaceVariant) }
        items(visible, key = { it.id }) { spec ->
            Card(onClick = { onSelect(spec) }, colors = CardDefaults.cardColors(containerColor = StudioColors.surface), shape = MaterialTheme.shapes.large) {
                Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = MaterialTheme.shapes.medium, color = Color(0xFF2C2340)) {
                        Box(Modifier.size(52.dp), contentAlignment = Alignment.Center) { Text(categoryGlyph(spec.category), color = StudioColors.primary, fontSize = 26.sp) }
                    }
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(spec.title, style = MaterialTheme.typography.titleMedium)
                        Text(spec.description, style = MaterialTheme.typography.bodySmall, color = StudioColors.onSurfaceVariant)
                        Text("${spec.category}  ·  ${spec.controls.count { !it.demo }} controls", style = MaterialTheme.typography.labelSmall, color = StudioColors.secondary)
                    }
                    Text("↗", color = StudioColors.primary, fontSize = 22.sp)
                }
            }
        }
        if (visible.isEmpty()) item { Text("No matching components. Try another name or category.", Modifier.padding(vertical = 32.dp)) }
    }
}

private fun categoryGlyph(category: String) = when (category) {
    "Surfaces" -> "▣"; "Interactions" -> "↔"; "Shapes" -> "◈"; "Loaders" -> "◌"; else -> "≈"
}

@Composable
private fun ComponentEditor(spec: ComponentSpec, settings: ComponentSettings) {
    var replay by remember { mutableIntStateOf(0) }
    var event by remember { mutableStateOf("Ready to explore") }
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }
    // Read the settings during composition: code changes with exactly the same values as the preview.
    val code = exportCode(spec, settings)
    LaunchedEffect(code) { copied = false }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(spec.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(spec.id, style = MaterialTheme.typography.labelSmall, fontFamily = FontFamily.Monospace, color = StudioColors.onSurfaceVariant)
            }
            TextButton(onClick = { settings.reset(); replay++; event = "Defaults restored" }) { Text("Reset") }
        }
        BoxWithConstraints(Modifier.weight(1f)) {
            val wide = maxWidth >= 760.dp
            val compact = maxHeight < 600.dp
            val preview: @Composable (Modifier) -> Unit = { modifier ->
                PreviewPane(spec, settings, replay, event, { event = it }, { replay++; event = "Demo restarted" }, modifier)
            }
            val editor: @Composable (Modifier) -> Unit = { modifier ->
                Column(modifier) {
                    PrimaryTabRow(selectedTabIndex = tab) {
                        listOf("Customize", "Kotlin code", "Guide").forEachIndexed { index, title -> Tab(selected = tab == index, onClick = { tab = index }, text = { Text(title) }) }
                    }
                    when (tab) {
                        0 -> SettingsControls(spec, settings)
                        1 -> Column(Modifier.fillMaxSize().padding(16.dp)) {
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text("Your current configuration", Modifier.weight(1f), style = MaterialTheme.typography.labelLarge)
                                FilledTonalButton(onClick = {
                                    context.getSystemService(ClipboardManager::class.java).setPrimaryClip(ClipData.newPlainText("Kosmos ${spec.title}", code))
                                    copied = true
                                }) { Text(if (copied) "Copied ✓" else "Copy code") }
                            }
                            SelectionContainer(Modifier.weight(1f).verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState())) {
                                Text(code, fontFamily = FontFamily.Monospace, fontSize = 12.sp, lineHeight = 19.sp, color = Color(0xFFBFE8DC), modifier = Modifier.padding(vertical = 16.dp))
                            }
                        }
                        else -> ComponentGuide(spec)
                    }
                }
            }
            if (wide) Row(Modifier.fillMaxSize()) {
                preview(Modifier.weight(1f).fillMaxHeight())
                VerticalDivider()
                editor(Modifier.weight(1f).fillMaxHeight())
            } else Column(Modifier.fillMaxSize()) {
                preview(Modifier.fillMaxWidth().height(if (compact) 250.dp else 360.dp))
                editor(Modifier.weight(1f).fillMaxWidth())
            }
        }
    }
}

@Composable
private fun PreviewPane(spec: ComponentSpec, settings: ComponentSettings, replay: Int, event: String, report: (String) -> Unit, restart: () -> Unit, modifier: Modifier) {
    var fullscreen by remember { mutableStateOf(false) }
    Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("● LIVE PREVIEW", Modifier.weight(1f), color = StudioColors.secondary, style = MaterialTheme.typography.labelSmall, letterSpacing = 1.sp)
            TextButton(onClick = { fullscreen = true }, contentPadding = PaddingValues(horizontal = 8.dp)) { Text("Expand") }
            TextButton(onClick = restart, contentPadding = PaddingValues(horizontal = 8.dp)) { Text("Replay") }
        }
        Surface(Modifier.weight(1f).fillMaxWidth(), color = settings.color("stageColor"), shape = MaterialTheme.shapes.large) {
            Box(Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
                ComponentDemo(spec, settings, replay, report)
            }
        }
        Text(event, color = StudioColors.secondary, style = MaterialTheme.typography.labelSmall, maxLines = 1)
    }
    if (fullscreen) Dialog(onDismissRequest = { fullscreen = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(Modifier.fillMaxSize(), color = settings.color("stageColor")) {
            Column(Modifier.fillMaxSize().systemBarsPadding().padding(20.dp)) {
                FilledTonalButton(onClick = { fullscreen = false }) { Text("Close preview") }
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    ComponentDemo(spec, settings, replay, report)
                }
            }
        }
    }
}

@Composable
private fun ComponentGuide(spec: ComponentSpec) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("What you can build", style = MaterialTheme.typography.titleMedium)
        Text(spec.description)
        Text("Try the interaction", style = MaterialTheme.typography.titleMedium)
        Text(spec.interaction)
        Text("Use it in your app", style = MaterialTheme.typography.titleMedium)
        Text("Customize the arguments, open Kotlin code and copy the example. Add the Kosmos library to your project, then paste the composable and imports. Sample content and callbacks are included so you can replace them with your own UI and application behavior.")
        Text("Color values", style = MaterialTheme.typography.titleMedium)
        Text("All component color arguments support an alpha channel. Eight-digit hex uses AARRGGBB; six-digit hex uses opaque RRGGBB. Transparent colors reveal the demo backdrop.")
        if (spec.id == "KosmosNeumorphicCard") Text("For soft neumorphic depth, match the demo backdrop to Surface color, then balance Light shadow color and Dark shadow color. Raised uses outer shadows, Pressed uses inner shadows, and Flat uses a border.")
        if (spec.id == "KosmosGlassCard") Text("The glass card draws a translucent tint and a border. It does not blur content behind it.")
        if (spec.id == "KosmosGradientButton") Text("Enabled controls interaction. This component does not automatically dim its colors when disabled.")
        Text("Layout & content slots", style = MaterialTheme.typography.titleMedium)
        Text("The preview uses a fixed demonstration area. The exported modifier shows that sizing explicitly; adjust it to your own layout. Components with content slots accept your own composables beyond the sample text and icons shown here.")
    }
}
