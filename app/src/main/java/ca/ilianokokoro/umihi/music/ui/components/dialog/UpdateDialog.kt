package ca.ilianokokoro.umihi.music.ui.components.dialog
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope

@Composable
fun UpdateDialog(scope: CoroutineScope) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val prefs = remember { context.getSharedPreferences("umihi_ui_prefs", Context.MODE_PRIVATE) }
    var showDialog by remember { mutableStateOf(!prefs.getBoolean("welcome_injector_v1_0", false)) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { prefs.edit().putBoolean("welcome_injector_v1_0", true).apply(); showDialog = false },
            title = { Text("Injector", fontWeight = FontWeight.Bold, fontSize = 24.sp) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Esta aplicación está diseñada con cariño y aprecio hacia ilianoKokoro por ser el Diseñador oficial de este proyecto.")
                    Text("• Synzoler Co/Diseñador", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    SocialRow("GitHub: https://github.com/synzoler", Color(0xFFE8EAED), Color(0xFF181717), "M12 0C5.37 0 0 5.37 0 12c0 5.31 3.435 9.795 8.205 11.385.6.105.825-.255.825-.57 0-.285-.015-1.23-.015-2.235-3.015.555-3.795-.735-4.035-1.41-.135-.345-.72-1.41-1.23-1.695-.42-.225-1.02-.78-.015-.795.945-.015 1.62.87 1.845 1.23 1.08 1.815 2.805 1.305 3.495.99.105-.78.42-1.305.765-1.605-2.67-.3-5.46-1.335-5.46-5.925 0-1.305.465-2.385 1.23-3.225-.12-.3-.54-1.53.12-3.18 0 0 1.005-.315 3.3 1.23.96-.27 1.98-.405 3-.405s2.04.135 3 .405c2.295-1.56 3.3-1.23 3.3-1.23.66 1.65.24 2.88.12 3.18.765.84 1.23 1.905 1.23 3.225 0 4.605-2.805 5.625-5.475 5.925.435.375.81 1.095.81 2.22 0 1.605-.015 2.895-.015 3.3 0 .315.225.69.825.57A12.02 12.02 0 0024 12c0-6.63-5.37-12-12-12z") { runCatching { uriHandler.openUri("https://github.com/synzoler") } }
                    SocialRow("Telegram: t.me/synzoler", Color(0xFFD9F0FA), Color(0xFF26A5E4), "M11.944 0A12 12 0 0 0 0 12a12 12 0 0 0 12 12 12 12 0 0 0 12-12A12 12 0 0 0 12 0a12 12 0 0 0-.056 0zm4.962 7.224c.1-.002.321.023.465.14a.506.506 0 0 1 .171.325c.016.093.036.306.02.472-.18 1.898-.962 6.502-1.36 8.627-.168.9-.499 1.201-.82 1.23-.696.065-1.225-.46-1.9-.902-1.056-.693-1.653-1.124-2.678-1.8-1.185-.78-.417-1.21.258-1.91.177-.184 3.247-2.977 3.307-3.23.007-.032.014-.15-.056-.212s-.174-.041-.249-.024c-.106.024-1.793 1.14-5.061 3.345-.48.33-.913.49-1.302.48-.428-.008-1.252-.241-1.865-.44-.752-.245-1.349-.374-1.297-.789.027-.216.325-.437.893-.663 3.498-1.524 5.83-2.529 6.998-3.014 3.332-1.386 4.025-1.627 4.476-1.635z") { runCatching { uriHandler.openUri("https://t.me/synzoler") } }
                    SocialRow("PayPal (Donar): paypal.me/synzoler", Color(0xFFDCE8FA), Color(0xFF00457C), "M7.076 21.337H2.47a.641.641 0 0 1-.633-.74L4.944.901C5.026.382 5.474 0 5.998 0h7.46c2.57 0 4.578.543 5.69 1.81 1.01 1.15 1.304 2.42 1.012 4.287-.023.143-.047.288-.077.437-.983 5.05-4.349 6.797-8.647 6.797h-2.19c-.524 0-.968.382-1.05.9l-1.12 7.106z") { runCatching { uriHandler.openUri("https://paypal.me/synzoler") } }
                    Text("Made with ♥ by Synzoler", modifier = Modifier.fillMaxWidth().padding(top = 4.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = { TextButton(onClick = { prefs.edit().putBoolean("welcome_injector_v1_0", true).apply(); showDialog = false }) { Text("Aceptar", fontWeight = FontWeight.Bold) } }
        )
    }
}

@Composable
private fun SocialRow(label: String, bg: Color, tint: Color, svg: String, onClick: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).clickable(onClick = onClick), color = bg) {
        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Canvas(modifier = Modifier.size(20.dp)) {
                val p = PathParser().parsePathString(svg).toPath()
                val m = android.graphics.Matrix().apply { setScale(size.width / 24f, size.height / 24f) }
                p.asAndroidPath().transform(m)
                drawPath(p, tint)
            }
            Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1C1B1F))
        }
    }
}