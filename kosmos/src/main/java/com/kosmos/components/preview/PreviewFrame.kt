package com.kosmos.components.preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Shared presentation for the component previews; not part of the public API. */
@Composable
internal fun PreviewFrame(title: String, hint: String, light: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (light) lightColorScheme() else darkColorScheme()) {
        Surface {
            Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(hint, style = MaterialTheme.typography.bodySmall)
                content()
            }
        }
    }
}

/** A single scrollable catalog for each component, including independent interactive examples. */
@Composable
internal fun PreviewGallery(title: String, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme()) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(title, style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "Styles, effects and interactions. Enable Interactive Preview to scroll, animate and try the controls.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                content()
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
