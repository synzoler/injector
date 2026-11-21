package ca.ilianokokoro.umihi.music.ui.components.dialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ilianokokoro.umihi.music.ui.theme.rememberStrings

@Composable
fun AboutInjectorDialog(onDismiss: () -> Unit) {
    val tr = rememberStrings()
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(tr.about, fontWeight = FontWeight.Bold, fontSize = 22.sp) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(tr.aboutBody)
                Text("${tr.versionLabel}: 1.0.0 (${tr.universalWord})", fontWeight = FontWeight.SemiBold)
                Text("${tr.system}: ${tr.detectSystem}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Made with ♥ by Synzoler", modifier = Modifier.fillMaxWidth().padding(top = 6.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall)
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text(tr.accept, fontWeight = FontWeight.Bold) } }
    )
}