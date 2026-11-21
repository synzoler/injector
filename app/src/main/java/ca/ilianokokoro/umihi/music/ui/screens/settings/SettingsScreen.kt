package ca.ilianokokoro.umihi.music.ui.screens.settings

import android.app.Activity
import android.app.Application
import android.content.Context
import android.text.format.Formatter
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.ilianokokoro.umihi.music.R
import ca.ilianokokoro.umihi.music.core.managers.VersionManager
import ca.ilianokokoro.umihi.music.data.repositories.DatastoreRepository.PreferenceKeys
import ca.ilianokokoro.umihi.music.extensions.folderDisplayPath
import ca.ilianokokoro.umihi.music.models.enums.ThemeMode
import ca.ilianokokoro.umihi.music.ui.components.ErrorMessage
import ca.ilianokokoro.umihi.music.ui.components.LoadingAnimation
import ca.ilianokokoro.umihi.music.ui.components.StatusBarWrapper
import ca.ilianokokoro.umihi.music.ui.components.bottomsheet.CacheSizeInputBottomSheet
import ca.ilianokokoro.umihi.music.ui.components.dialog.ConfirmDialog
import ca.ilianokokoro.umihi.music.ui.components.dialog.DownloadLocationDialog
import ca.ilianokokoro.umihi.music.ui.icons.*
import ca.ilianokokoro.umihi.music.ui.navigation.viewmodels.SharedViewModel
import ca.ilianokokoro.umihi.music.ui.theme.*
import ca.ilianokokoro.umihi.music.ui.theme.rememberStrings
@Composable
fun SettingsScreen(openAuthScreen: () -> Unit, application: Application, sharedViewModel: SharedViewModel, settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory(sharedViewModel, application))) {
    val context = LocalContext.current
    val uiState = settingsViewModel.uiState.collectAsStateWithLifecycle().value
    var showLangDialog by remember { mutableStateOf(false) }
    var showLookDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    val prefs = remember { context.getSharedPreferences("umihi_ui_prefs", Context.MODE_PRIVATE) }
    var currentLang by remember { mutableStateOf(prefs.getString("app_language", "") ?: "") }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, ev -> if (ev == Lifecycle.Event.ON_RESUME) { settingsViewModel.getSettings(); settingsViewModel.getHiddenPlaylists() } }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    val folderPicker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri -> settingsViewModel.onDownloadFolderPicked(uri) }
    StatusBarWrapper { statusBarHeight ->
        Scaffold(containerColor = MaterialTheme.colorScheme.background, contentWindowInsets = WindowInsets(0.dp)) { paddingValues ->
            val tr = rememberStrings()
            when (val ss = uiState.screenState) {
                ScreenState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { LoadingAnimation() }
                is ScreenState.Error -> ErrorMessage(ex = ss.exception, onRetry = settingsViewModel::getSettings)
                is ScreenState.Success -> {
                    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(start = 18.dp, end = 18.dp, top = statusBarHeight + 12.dp, bottom = 120.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(text = tr.settings, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        PastelBigCard(PastelLilacBg, PastelLilacAccent, if (settingsViewModel.isLoggedIn()) logout else login, tr.googleAccount, if (settingsViewModel.isLoggedIn()) stringResource(R.string.session_active_sub) else stringResource(R.string.session_inactive_sub)) { if (settingsViewModel.isLoggedIn()) settingsViewModel.logOut() else openAuthScreen() }
Text(text = tr.quickToggles, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    QuickToggleCard(Modifier.weight(1f), "Auto-update", autorenew, ss.settings.updateChecking, getAccentColor()) { settingsViewModel.updateSetting(PreferenceKeys.AUTO_UPDATE, !ss.settings.updateChecking) }
    QuickToggleCard(Modifier.weight(1f), "Wi-Fi only", cloud_download, !ss.settings.downloadOnMetered, getAccentColor()) { settingsViewModel.updateSetting(PreferenceKeys.DOWNLOAD_ON_METERED, !ss.settings.downloadOnMetered) }
}
Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
    QuickToggleCard(Modifier.weight(1f), "Audio Offload", memory, ss.settings.useAudioOffload, getAccentColor()) { settingsViewModel.updateAudioOffloadSetting(!ss.settings.useAudioOffload) }
    QuickToggleCard(Modifier.weight(1f), "Offline Mode", cloud_off, ss.settings.offlineMode, ActivePurpleToggle) { settingsViewModel.updateOfflineModeSetting(!ss.settings.offlineMode) }
}
Text(text = tr.everythingElse, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
val loc = runCatching { ss.settings.downloadLocation?.folderDisplayPath() }.getOrNull() ?: "Interno"
val uAudio = Formatter.formatShortFileSize(application, uiState.downloadsUsage.audioBytes)
val uImg = Formatter.formatShortFileSize(application, uiState.downloadsUsage.imageBytes)
PastelBigCard(PastelBlueBg, PastelBlueAccent, folder, tr.location, "Carpeta: $loc\nUsado: $uAudio • $uImg") { settingsViewModel.updateShowDownloadLocationDialog(true) }
PastelBigCard(PastelMintBg, PastelMintAccent, info, tr.appLanguage, tr.selectLanguageSub) { showLangDialog = true }
PastelBigCard(PastelLilacBg, PastelLilacAccent, palette, tr.lookAndFeel, tr.lookAndFeelSub, showColorDots = true) { showLookDialog = true }
val cAudio = Formatter.formatShortFileSize(application, uiState.audioCacheUsed)
PastelBigCard(PastelPinkBg, PastelPinkAccent, memory, tr.cacheAudio, "${tr.used}: $cAudio / ${ss.settings.exoPlayerCacheSizeMB} MB") { settingsViewModel.updateShowCacheSizeInputSheet(true, CacheType.AUDIO) }
PastelBigCard(PastelCreamBg, PastelCreamAccent, delete, tr.storageCleanup, tr.storageCleanupSub) { settingsViewModel.updateShowCacheClearConfirm(true) }
PastelBigCard(androidx.compose.ui.graphics.Color(0xFFD4F0F7), androidx.compose.ui.graphics.Color(0xFF1A6073), info, tr.about, tr.universalVersion) { showAboutDialog = true }
if (showLangDialog) {
    val langs = listOf("" to tr.system, "es" to "Español", "en" to "English", "pt" to "Português", "fr" to "Français", "de" to "Deutsch", "it" to "Italiano", "ja" to "日本語", "ko" to "앜국무", "zh" to "中文", "ru" to "Русский", "ar" to "العربية", "hi" to "हिन耦ी", "tr" to "Türkçe", "nl" to "Nederlands", "pl" to "Polski", "sv" to "Svenska", "id" to "Bahasa Indonesia", "vi" to "Tiếng Việt", "th" to "ไทย", "uk" to "Українська", "cs" to "Čeština", "el" to "Ελληνικά", "ro" to "Română", "hu" to "Magyar")
    AlertDialog(onDismissRequest = { showLangDialog = false }, title = { Text(stringResource(R.string.app_language)) }, text = {
        Column(Modifier.heightIn(max = 340.dp).verticalScroll(rememberScrollState())) {
            langs.forEach { (code, name) ->
                Row(Modifier.fillMaxWidth().clickable { prefs.edit().putString("app_language", code).apply(); currentLang = code; showLangDialog = false; (context as? Activity)?.recreate() }.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = currentLang == code, onClick = null)
                    Text(name, fontSize = 15.sp)
                }
            }
        }
    }, confirmButton = { TextButton(onClick = { showLangDialog = false }) { Text(stringResource(R.string.close_btn)) } })
}
else if (showLookDialog) {
    AlertDialog(onDismissRequest = { showLookDialog = false }, text = {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(tr.appTheme, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(ThemeMode.LIGHT to tr.light, ThemeMode.DARK to tr.dark, ThemeMode.SYSTEM to tr.system).forEach { (mode, label) ->
                    val sel = ss.settings.themeMode == mode
                    Box(Modifier.clip(CircleShape).background(if (sel) getAccentColor() else InactiveToggleBg).clickable { settingsViewModel.updateSetting(PreferenceKeys.THEME_MODE, mode.name) }.padding(horizontal = 14.dp, vertical = 8.dp)) {
                        Text(label, color = if (sel) Color.White else TextPrimary, fontSize = 13.sp)
                    }
                }
            }
            Text(tr.accentColor, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf(PastelMintAccent, PastelBlueAccent, PastelCreamAccent, PastelPinkAccent, PastelLilacAccent).forEachIndexed { idx, col ->
                    Box(Modifier.size(38.dp).clip(CircleShape).background(col).border(if (currentAccentIndex == idx) 3.dp else 0.dp, Color.White, CircleShape).clickable { currentAccentIndex = idx; prefs.edit().putInt("accent_index", idx).apply(); showLookDialog = false })
                }
            }
        }
    }, confirmButton = { TextButton(onClick = { showLookDialog = false }) { Text(tr.done) } })
}
else if (showAboutDialog) {
    AlertDialog(onDismissRequest = { showAboutDialog = false }, title = { Text(tr.about) }, text = {
        Text("${tr.aboutBody}\n\n${tr.versionLabel}: V1.0.0 (${tr.universalWord})\n${tr.system}: Android ${android.os.Build.VERSION.RELEASE} (${android.os.Build.VERSION.SDK_INT} SDK)")
    }, confirmButton = { TextButton(onClick = { showAboutDialog = false }) { Text(tr.accept) } })
}
if (uiState.showCacheSizeInputSheet) { val init = if (uiState.cacheTypeForInput == CacheType.AUDIO) ss.settings.exoPlayerCacheSizeMB else ss.settings.thumbnailCacheSizeMB; CacheSizeInputBottomSheet(cacheType = uiState.cacheTypeForInput, initialSizeMB = init, onConfirm = { settingsViewModel.saveCacheSize(it, uiState.cacheTypeForInput); settingsViewModel.updateShowCacheSizeInputSheet(false, uiState.cacheTypeForInput) }) }
else if (uiState.showCacheClearConfirm) { ConfirmDialog(title = stringResource(R.string.clear_cache), text = stringResource(R.string.clear_cache_message), onConfirm = { settingsViewModel.clearCache(); settingsViewModel.updateShowCacheClearConfirm(false) }, onDismiss = { settingsViewModel.updateShowCacheClearConfirm(false) }) }
else if (uiState.showDownloadLocationDialog) { DownloadLocationDialog(onConfirm = { folderPicker.launch(ss.settings.downloadLocation); settingsViewModel.updateShowDownloadLocationDialog(false) }, onReset = { settingsViewModel.resetDownloadLocation(); settingsViewModel.updateShowDownloadLocationDialog(false) }, onDismiss = { settingsViewModel.updateShowDownloadLocationDialog(false) }) }
} } } } } }
@Composable
private fun QuickToggleCard(modifier: Modifier, title: String, icon: ImageVector, isActive: Boolean, activeColor: Color, onClick: () -> Unit) { val bg = if (isActive) activeColor else InactiveToggleBg; val fg = if (isActive) Color.White else TextPrimary; Row(modifier = modifier.clip(RoundedCornerShape(20.dp)).background(bg).clickable { onClick() }.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) { Icon(icon, title, tint = fg, modifier = Modifier.size(20.dp)); Column { Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = fg, maxLines = 1); Text(text = if (isActive) "On" else "Off", fontSize = 11.sp, color = fg.copy(alpha = 0.75f)) } } }
@Composable
private fun PastelBigCard(bgColor: Color, accentColor: Color, icon: ImageVector, title: String, subtitle: String, showColorDots: Boolean = false, onClick: () -> Unit) { Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)).background(bgColor).clickable { onClick() }.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) { Box(Modifier.size(44.dp).clip(CircleShape).background(accentColor.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) { Icon(icon, title, tint = accentColor, modifier = Modifier.size(22.dp)) }; Column(verticalArrangement = Arrangement.spacedBy(4.dp)) { Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary); Text(text = subtitle, fontSize = 13.sp, color = TextSecondary, lineHeight = 18.sp) }; if (showColorDots) { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf(PastelMintAccent, PastelBlueAccent, PastelCreamAccent, PastelPinkAccent, PastelLilacAccent).forEach { Box(Modifier.size(22.dp).clip(CircleShape).background(it)) } } } } }
