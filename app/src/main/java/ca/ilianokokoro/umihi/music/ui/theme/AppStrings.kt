package ca.ilianokokoro.umihi.music.ui.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

data class UIStrings(
    val settings: String,
    val googleAccount: String,
    val quickToggles: String,
    val everythingElse: String,
    val location: String,
    val appLanguage: String,
    val selectLanguageSub: String,
    val lookAndFeel: String,
    val lookAndFeelSub: String,
    val cacheAudio: String,
    val used: String,
    val storageCleanup: String,
    val storageCleanupSub: String,
    val about: String,
    val universalVersion: String,
    val home: String,
    val library: String,
    val forYou: String,
    val albums: String,
    val artists: String,
    val energy: String,
    val recommendationsForYou: String,
    val searchPlaceholder: String,
    val searchShort: String,
    val exploreByGenre: String,
    val all: String,
    val downloads: String,
    val playlists: String,
    val liked: String,
    val yourPlaylists: String,
    val songsCount: String,
    val appTheme: String,
    val light: String,
    val dark: String,
    val system: String,
    val accentColor: String,
    val done: String,
    val mix1Title: String, val mix1Sub: String,
    val mix2Title: String, val mix2Sub: String,
    val mix3Title: String, val mix3Sub: String,
    val mix4Title: String, val mix4Sub: String,
    val mix5Title: String, val mix5Sub: String,
    val genres: List<String>,
    val aboutBody: String,
    val versionLabel: String,
    val universalWord: String,
    val detectSystem: String,
    val accept: String
)

@Composable
fun rememberStrings(): UIStrings {
    val context = LocalContext.current
    val config = LocalConfiguration.current
    val saved = context.getSharedPreferences("umihi_ui_prefs", Context.MODE_PRIVATE)
        .getString("app_language", "") ?: ""
    val lang = if (saved.isNotBlank()) saved.lowercase()
    else Locale.getDefault().language.lowercase()

    return when {
        lang.startsWith("es") -> UIStrings(
            "Ajustes","Cuenta de Google","Ajustes rápidos","Todo lo demás","Ubicación","Idioma de la app",
            "Selecciona tu idioma (25 idiomas disponibles)","Apariencia","Tema Claro/Oscuro y colores de acento",
            "Caché y Audio","Usado","Almacenamiento y limpieza","Limpiar archivos temporales y caché",
            "Acerca","Injector V1.0.0 (Universal)","Inicio","Biblioteca","Para ti","Álbumes","Artistas","Energía",
            "Recomendaciones para ti","Buscar canciones, artistas...","Buscar","Explorar por género",
            "Todo","Descargas","Playlists","Me gusta","Tus playlists","canciones",
            "Tema de la app","Claro","Oscuro","Sistema","Color de acento","Listo",
            "Descubrimiento semanal","Mix de tus favoritos y novedades",
            "Mix diario","Pop, Electrónica e Indie",
            "Álbumes del momento","Los lanzamientos más escuchados",
            "Artistas recomendados","Descubre nuevos artistas",
            "Energía y Dance","Ritmos activos para el día",
            listOf("Dance","Pop","Rock","Alternativo","Hip-Hop","Electrónica","Reggaetón","Indie"),"Un sencillo reproductor de música de YouTube para Android, basado en Material Design, creado con Kotlin y Jetpack Compose.","Versión","Universal","Detectar Sistema","Aceptar"
        )
        lang.startsWith("pt") -> UIStrings(
            "Configurações","Conta do Google","Alternâncias rápidas","Todo o resto","Localização","Idioma do app",
            "Selecione seu idioma (25 idiomas disponíveis)","Aparência","Tema Claro/Escuro e cores de destaque",
            "Cache e Áudio","Usado","Armazenamento e limpeza","Limpar arquivos temporários e cache",
            "Sobre","Injector V1.0.0 (Universal)","Início","Biblioteca","Para você","Álbuns","Artistas","Energia",
            "Recomendações para você","Buscar músicas, artistas...","Buscar","Explorar por gênero",
            "Tudo","Downloads","Playlists","Curtidas","Suas playlists","músicas",
            "Tema do app","Claro","Escuro","Sistema","Cor de destaque","Pronto",
            "Descobertas da semana","Mix dos seus favoritos e novidades",
            "Mix diário","Pop, Eletrônica e Indie",
            "Álbuns do momento","Os lançamentos mais ouvidos",
            "Artistas recomendados","Descubra novos artistas",
            "Energia e Dance","Ritmos ativos para o dia",
            listOf("Dance","Pop","Rock","Alternativo","Hip-Hop","Eletrônica","Reggaeton","Indie"),"Um reprodutor de música simples do YouTube para Android, baseado em Material Design, criado com Kotlin e Jetpack Compose.","Versão","Universal","Detectar Sistema","Aceitar"
        )
        lang.startsWith("ru") -> UIStrings(
            "Настройки","Аккаунт Google","Быстрые переключатели","Всё остальное","Расположение","Язык приложения",
            "Выберите язык (доступно 25 языков)","Внешний вид","Светлая/тёмная тема и цвета акцента",
            "Кэш и аудио","Использовано","Хранилище и очистка","Очистить временные файлы и кэш",
            "О приложении","Injector V1.0.0 (Универсальная)","Главная","Библиотека","Для вас","Альбомы","Исполнители","Энергия",
            "Рекомендации для вас","Поиск треков, исполнителей...","Поиск","Обзор по жанрам",
            "Все","Загрузки","Плейлисты","Понравившиеся","Ваши плейлисты","треков",
            "Тема приложения","Светлая","Тёмная","Система","Цвет акцента","Готово",
            "Еженедельные открытия","Микс любимых треков и новинок",
            "Микс дня","Поп, электроника и инди",
            "Альбомы момента","Самые прослушиваемые новинки",
            "Рекомендуемые артисты","Открывайте новых исполнителей",
            "Энергия и танцы","Активные ритмы на весь день",
            listOf("Танцевальная","Поп","Рок","Альтернатива","Хип-хоп","Электроника","Реггетон","Инди"),"Простой музыкальный плеер YouTube для Android на основе Material Design, созданный на Kotlin и Jetpack Compose.","Версия","Универсальная","Определить систему","Принять"
        )
        lang.startsWith("ar") -> UIStrings("الإعدادات","حساب Google","تبديلات سريعة","كل شيء آخر","الموقع","لغة التطبيق","اختر لغتك (25 لغة متاحة)","المظهر","السمة الفاتحة/الداكنة وألوان التمييز","ذاكرة التخزين المؤقت والصوت","مستخدم","التخزين والتنظيف","مسح الملفات المؤقتة وذاكرة التخزين المؤقت","حول","Injector V1.0.0 (عالمي)","الرئيسية","المكتبة","لك","الألبومات","الفنانون","طاقة","توصيات لك","ابحث عن أغاني، فنانين...","بحث","استكشاف حسب النوع","الكل","التنزيلات","قوائم التشغيل","أعجبني","قوائم التشغيل الخاصة بك","أغاني","سمة التطبيق","فاتح","داكن","النظام","لون التمييز","تم","اكتشاف أسبوعي","مزيج من المفضلة والإصدارات الجديدة","مزيج يومي","بوب وإلكترونيك وإندي","ألبومات رائجة","أكثر الإصدارات استماعاً","فنانون موصى بهم","اكتشف فنانين جدد","طاقة ورقص","إيقاعات نشطة ليومك",listOf("دانس","بوب","روك","بديل","هيب هوب","إلكترونيك","ريغيتون","إندي"),"مشغل موسيقى YouTube بسيط لنظام Android، يعتمد على Material Design، تم إنشاؤه باستخدام Kotlin وJetpack Compose.","الإصدار","عالمي","اكتشاف النظام","موافق")
        lang.startsWith("tr") -> UIStrings("Ayarlar","Google Hesabı","Hızlı geçişler","Diğer her şey","Konum","Uygulama dili","Dilinizi seçin (25 dil mevcut)","Görünüm","Açık/Koyu tema ve vurgu renkleri","Önbellek ve Ses","Kullanılan","Depolama ve Temizlik","Geçici dosyaları ve önbelleği temizle","Hakkında","Injector V1.0.0 (Evrensel)","Ana Sayfa","Kitaplık","Senin için","Albümler","Sanatçılar","Enerji","Senin için öneriler","Şarkı, sanatçı ara...","Ara","Türe göre keşfet","Tümü","İndirilenler","Çalma listeleri","Beğenilenler","Çalma listelerin","şarkı","Uygulama teması","Açık","Koyu","Sistem","Vurgu rengi","Bitti","Haftalık Keşif","Favorilerin ve yeni çıkanların karışımı","Günlük Mix","Pop, Elektronik ve Indie","Popüler Albümler","En çok dinlenen yeni çıkanlar","Önerilen Sanatçılar","Yeni sanatçılar keşfet","Enerji ve Dans","Günün için hareketli ritimler",listOf("Dans","Pop","Rock","Alternatif","Hip-Hop","Elektronik","Reggaeton","Indie"),"Material Design tabanlı, Kotlin ve Jetpack Compose ile oluşturulmuş basit bir Android YouTube müzik çaları.","Sürüm","Evrensel","Sistemi Algıla","Tamam")
        lang.startsWith("zh") -> UIStrings("设置","Google 账号","快捷开关","其他","存储位置","应用语言","选择语言（支持 25 种语言）","外观","浅色/深色主题与强调色","缓存与音频","已使用","存储与清理","清理临时文件和缓存","关于","Injector V1.0.0 (通用版)","首页","媒体库","为你推荐","专辑","艺术家","活力","为你推荐","搜索歌曲、艺术家...","搜索","按流派浏览","全部","下载","播放列表","我喜欢的","你的播放列表","首歌曲","应用主题","浅色","深色","系统","强调色","完成","每周发现","精选喜爱与最新单曲","每日精选","流行、电子与独立","热门专辑","最受欢迎的新专辑","推荐艺术家","发现新艺术家","活力与舞曲","全天动感节奏",listOf("舞曲","流行","摇滚","另类","嘻哈","电子","雷鬼顿","独立"),"一款基于 Material Design 的简洁 Android YouTube 音乐播放器，使用 Kotlin 和 Jetpack Compose 构建。","版本","通用版","检测系统","确定")
        lang.startsWith("ja") -> UIStrings("設定","Googleアカウント","クイックトグル","その他","保存場所","アプリの言語","言語を選択（25言語対応）","外観","ライト/ダークテーマとアクセントカラー","キャッシュとオーディオ","使用済み","ストレージとクリーンアップ","一時ファイルとキャッシュを削除","情報","Injector V1.0.0 (ユニバーサル)","ホーム","ライブラリ","おすすめ","アルバム","アーティスト","エナジー","あなたへのおすすめ","曲、アーティストを検索...","検索","ジャンルから探す","すべて","ダウンロード","プレイリスト","高評価","マイプレイリスト","曲","アプリテーマ","ライト","ダーク","システム","アクセントカラー","完了","ウィークリー・ディスカバリー","お気に入りと新曲のミックス","デイリーミックス","ポップ、エレクトロニック、インディー","注目のアルバム","人気の最新リリース","おすすめアーティスト","新しいアーティストを発見","エナジー＆ダンス","一日のためのアップビートなリズム",listOf("ダンス","ポップ","ロック","オルタナティブ","ヒップホップ","エレクトロニック","レゲトン","インディー"),"Material Design に基づき、Kotlin と Jetpack Compose で作成されたシンプルな Android 用 YouTube ミュージックプレーヤー。","バージョン","ユニバーサル","システムを検出","OK")
        lang.startsWith("ko") -> UIStrings("설정","Google 계정","빠른 토글","기타","저장 위치","앱 언어","언어 선택(25개 언어 지원)","디자인","라이트/다크 테마 및 강조 색상","캐시 및 오디오","사용됨","저장공간 및 정리","임시 파일 및 캐시 정리","정보","Injector V1.0.0 (유니버설)","홈","보관함","맞춤 추천","앨범","아티스트","에너지","회원님을 위한 추천","노래, 아티스트 검색...","검색","장르별 탐색","전체","다운로드","재생목록","좋아요","내 재생목록","곡","앱 테마","라이트","다크","시스템","강조 색상","완료","위클리 디스커버리","즐겨찾기와 신곡 믹스","데일리 믹스","팝, 일렉트로닉 및 인디","인기 앨범","가장 많이 들은 신보","추천 아티스트","새로운 아티스트 발견","에너지 & 댄스","하루를 위한 신나는 리듬",listOf("댄스","팝","록","얼터너티브","힙합","일렉트로닉","레게톤","인디"),"Material Design을 기반으로 Kotlin과 Jetpack Compose로 제작된 간편한 Android용 YouTube 뮤직 플레이어입니다.","버전","유니버설","시스템 감지","확인")
        lang.startsWith("fr") -> UIStrings("Paramètres","Compte Google","Bascules rapides","Tout le reste","Emplacement","Langue de l'application","Choisissez votre langue (25 langues disponibles)","Apparence","Thème Clair/Sombre et couleurs d'accent","Cache et Audio","Utilisé","Stockage et nettoyage","Nettoyer les fichiers temporaires et le cache","À propos","Injector V1.0.0 (Universel)","Accueil","Bibliothèque","Pour vous","Albums","Artistes","Énergie","Recommandations pour vous","Rechercher des titres, artistes...","Rechercher","Explorer par genre","Tout","Téléchargements","Playlists","Aimés","Vos playlists","titres","Thème de l'application","Clair","Sombre","Système","Couleur d'accent","Terminé","Découverte de la semaine","Mix de vos favoris et nouveautés","Mix quotidien","Pop, Électronique et Indie","Albums du moment","Les nouveautés les plus écoutées","Artistes recommandés","Découvrez de nouveaux artistes","Énergie et Danse","Rythmes dynamiques pour la journée",listOf("Dance","Pop","Rock","Alternatif","Hip-Hop","Électronique","Reggaeton","Indie"),"Un lecteur de musique YouTube simple pour Android, basé sur Material Design, créé avec Kotlin et Jetpack Compose.","Version","Universel","Détecter le système","Accepter")
        lang.startsWith("de") -> UIStrings("Einstellungen","Google-Konto","Schnellschalter","Alles andere","Speicherort","App-Sprache","Sprache auswählen (25 Sprachen verfügbar)","Erscheinungsbild","Helles/Dunkles Design und Akzentfarben","Cache & Audio","Belegt","Speicher & Bereinigung","Temporäre Dateien und Cache bereinigen","Über","Injector V1.0.0 (Universal)","Start","Bibliothek","Für dich","Alben","Künstler","Energie","Empfehlungen für dich","Songs, Künstler suchen...","Suchen","Nach Genre entdecken","Alle","Downloads","Playlists","Gefällt mir","Deine Playlists","Titel","App-Design","Hell","Dunkel","System","Akzentfarbe","Fertig","Mix der Woche","Mix aus Favoriten und Neuheiten","Daily Mix","Pop, Electronic und Indie","Aktuelle Alben","Die meistgehörten Neuerscheinungen","Empfohlene Künstler","Entdecke neue Künstler","Energie & Dance","Aktive Rhythmen für den Tag",listOf("Dance","Pop","Rock","Alternative","Hip-Hop","Electronic","Reggaeton","Indie"),"Ein einfacher YouTube-Musikplayer für Android, basierend auf Material Design, erstellt mit Kotlin und Jetpack Compose.","Version","Universal","System erkennen","Akzeptieren")
        lang.startsWith("it") -> UIStrings("Impostazioni","Account Google","Interruttori rapidi","Tutto il resto","Posizione","Lingua dell'app","Seleziona la tua lingua (25 lingue disponibili)","Aspetto","Tema Chiaro/Scuro e colori d'accento","Cache e Audio","Usato","Archiviazione e pulizia","Pulisci file temporanei e cache","Info","Injector V1.0.0 (Universale)","Home","Libreria","Per te","Album","Artisti","Energia","Consigliati per te","Cerca brani, artisti...","Cerca","Esplora per genere","Tutto","Download","Playlist","Piaciuti","Le tue playlist","brani","Tema dell'app","Chiaro","Scuro","Sistema","Colore d'accento","Fatto","Scoperta settimanale","Mix dei tuoi preferiti e novità","Daily Mix","Pop, Elettronica e Indie","Album del momento","Le novità più ascoltate","Artisti consigliati","Scopri nuovi artisti","Energia e Dance","Ritmi attivi per la giornata",listOf("Dance","Pop","Rock","Alternativo","Hip-Hop","Elettronica","Reggaeton","Indie"),"Un semplice lettore musicale YouTube per Android, basato su Material Design, creato con Kotlin e Jetpack Compose.","Versione","Universale","Rileva sistema","Accetta")
        lang.startsWith("pl") -> UIStrings("Ustawienia","Konto Google","Szybkie przełączniki","Wszystko inne","Lokalizacja","Język aplikacji","Wybierz swój język (25 dostępnych języków)","Wygląd","Motyw Jasny/Ciemny i kolory akcentów","Pamięć podręczna i dźwięk","Użyto","Pamięć i czyszczenie","Wyczyść pliki tymczasowe i pamięć podręczną","Informacje","Injector V1.0.0 (Uniwersalna)","Start","Biblioteka","Dla Ciebie","Albumy","Artyści","Energia","Polecane dla Ciebie","Szukaj utworów, wykonawców...","Szukaj","Przeglądaj według gatunku","Wszystko","Pobrane","Playlisty","Polubione","Twoje playlisty","utworów","Motyw aplikacji","Jasny","Ciemny","System","Kolor akcentu","Gotowe","Odkrycia tygodnia","Miks ulubionych i nowości","Daily Mix","Pop, Elektronika i Indie","Popularne albumy","Najczęściej słuchane nowości","Polecani artyści","Odkryj nowych artystów","Energia i Dance","Żywe rytmy na cały dzień",listOf("Dance","Pop","Rock","Alternatywa","Hip-Hop","Elektronika","Reggaeton","Indie"),"Prosty odtwarzacz muzyki YouTube dla Androida, oparty na Material Design, stworzony w Kotlin i Jetpack Compose.","Wersja","Uniwersalna","Wykryj system","Akceptuj")
        lang.startsWith("hi") -> UIStrings("सेटिंग्स","Google खाता","त्वरित टॉगल","बाकी सब","स्थान","ऐप की भाषा","अपनी भाषा चुनें (25 भाषाएं उपलब्ध)","दिखावट","लाइट/डार्क थीम और एक्सेंट रंग","कैश और ऑडियो","उपयोग किया गया","स्टोरेज और सफाई","अस्थायी फ़ाइलें और कैश साफ़ करें","के बारे में","Injector V1.0.0 (यूनिवर्सल)","होम","लाइब्रेरी","आपके लिए","एल्बम","कलाकार","ऊर्जा","आपके लिए सुझाव","गाने, कलाकार खोजें...","खोजें","शैली के अनुसार देखें","सभी","डाउनलोड","प्लेलिस्ट","पसंद किए गए","आपकी प्लेलिस्ट","गाने","ऐप थीम","लाइट","डार्क","सिस्टम","एक्सेंट रंग","हो गया","साप्ताहिक खोज","आपके पसंदीदा और नए गानों का मिक्स","डेली मिक्स","पॉप, इलेक्ट्रॉनिक और इंडी","ट्रेंडिंग एल्बम","सबसे ज्यादा सुने गए नए एल्बम","अनुशंसित कलाकार","नए कलाकार खोजें","ऊर्जा और डांस","आपके दिन के लिए ऊर्जावान धुनें",listOf("डांस","पॉप","रॉक","वैकल्पिक","हिप-हॉप","इलेक्ट्रॉनिक","रेगेटन","इंडी"),"Material Design पर आधारित, Kotlin और Jetpack Compose के साथ बनाया गया Android के लिए एक सरल YouTube संगीत प्लेयर।","संस्करण","यूनिवर्सल","सिस्टम का पता लगाएं","स्वीकार करें")
        lang.startsWith("in") || lang.startsWith("id") -> UIStrings("Setelan","Akun Google","Tombol cepat","Lainnya","Lokasi","Bahasa aplikasi","Pilih bahasa Anda (25 bahasa tersedia)","Tampilan","Tema Terang/Gelap dan warna aksen","Cache & Audio","Digunakan","Penyimpanan & Pembersihan","Bersihkan file sementara dan cache","Tentang","Injector V1.0.0 (Universal)","Beranda","Koleksi","Untuk Anda","Album","Artis","Energi","Rekomendasi untuk Anda","Cari lagu, artis...","Cari","Jelajahi berdasarkan genre","Semua","Unduhan","Playlist","Disukai","Playlist Anda","lagu","Tema aplikasi","Terang","Gelap","Sistem","Warna aksen","Selesai","Penemuan Mingguan","Campuran favorit dan rilis baru","Mix Harian","Pop, Elektronik, dan Indie","Album Populer","Rilis baru paling banyak didengar","Artis Rekomendasi","Temukan artis baru","Energi & Dance","Irama bersemangat untuk harimu",listOf("Dance","Pop","Rock","Alternatif","Hip-Hop","Elektronik","Reggaeton","Indie"),"Pemutar musik YouTube sederhana untuk Android, berbasis Material Design, dibuat dengan Kotlin dan Jetpack Compose.","Versi","Universal","Deteksi Sistem","Terima")
        lang.startsWith("vi") -> UIStrings("Cài đặt","Tài khoản Google","Chuyển đổi nhanh","Mọi thứ khác","Vị trí","Ngôn ngữ ứng dụng","Chọn ngôn ngữ của bạn (có sẵn 25 ngôn ngữ)","Giao diện","Chủ đề Sáng/Tối và màu nhấn","Bộ nhớ đệm & Âm thanh","Đã dùng","Bộ nhớ & Dọn dẹp","Xóa tệp tạm thời và bộ nhớ đệm","Giới thiệu","Injector V1.0.0 (Phổ quát)","Trang chủ","Thư viện","Dành cho bạn","Album","Nghệ sĩ","Năng lượng","Đề xuất cho bạn","Tìm bài hát, nghệ sĩ...","Tìm kiếm","Khám phá theo thể loại","Tất cả","Tải xuống","Danh sách phát","Đã thích","Danh sách phát của bạn","bài hát","Chủ đề ứng dụng","Sáng","Tối","Hệ thống","Màu nhấn","Xong","Khám phá hàng tuần","Kết hợp bài yêu thích và mới phát hành","Mix hàng ngày","Pop, Điện tử và Indie","Album thịnh hành","Bản phát hành mới nghe nhiều nhất","Nghệ sĩ đề xuất","Khám phá nghệ sĩ mới","Năng lượng & Dance","Giai điệu sôi động cho ngày mới",listOf("Dance","Pop","Rock","Alternative","Hip-Hop","Điện tử","Reggaeton","Indie"),"Trình phát nhạc YouTube đơn giản cho Android, dựa trên Material Design, được tạo bằng Kotlin và Jetpack Compose.","Phiên bản","Phổ quát","Phát hiện hệ thống","Chấp nhận")
        lang.startsWith("th") -> UIStrings("การตั้งค่า","บัญชี Google","ปุ่มลัด","อื่นๆ ทั้งหมด","ตำแหน่งที่ตั้ง","ภาษาของแอป","เลือกภาษาของคุณ (มี 25 ภาษา)","รูปลักษณ์","ธีมสว่าง/มืดและสีเน้น","แคชและเสียง","ใช้ไป","พื้นที่เก็บข้อมูลและการล้างข้อมูล","ล้างไฟล์ชั่วคราวและแคช","เกี่ยวกับ","Injector V1.0.0 (สากล)","หน้าแรก","คลังเพลง","สำหรับคุณ","อัลบั้ม","ศิลปิน","พลังงาน","แนะนำสำหรับคุณ","ค้นหาเพลง ศิลปิน...","ค้นหา","สำรวจตามแนวเพลง","ทั้งหมด","ดาวน์โหลด","เพลย์ลิสต์","ที่ถูกใจ","เพลย์ลิสต์ของคุณ","เพลง","ธีมแอป","สว่าง","มืด","ระบบ","สีเน้น","เสร็จสิ้น","ค้นพบประจำสัปดาห์","รวมเพลงโปรดและเพลงใหม่","มิกซ์ประจำวัน","ป๊อป อิเล็กทรอนิกส์ และอินดี้","อัลบั้มยอดนิยม","เพลงใหม่ที่ฟังมากที่สุด","ศิลปินแนะนำ","ค้นพบศิลปินใหม่","พลังงานและแดนซ์","จังหวะคึกคักสำหรับวันของคุณ",listOf("แดนซ์","ป๊อป","ร็อก","อัลเทอร์เนทีฟ","ฮิปฮอป","อิเล็กทรอนิกส์","เรกเกตอน","อินดี้"),"เครื่องเล่นเพลง YouTube ที่เรียบง่ายสำหรับ Android บนพื้นฐาน Material Design สร้างด้วย Kotlin และ Jetpack Compose","เวอร์ชัน","สากล","ตรวจจับระบบ","ตกลง")
        lang.startsWith("uk") -> UIStrings("Налаштування","Обліковий запис Google","Швидкі перемикачі","Усе інше","Розташування","Мова додатка","Виберіть мову (доступно 25 мов)","Зовнішній вигляд","Світла/темна тема та кольори акценту","Кеш і аудіо","Використано","Сховище та очищення","Очистити тимчасові файли та кеш","Про додаток","Injector V1.0.0 (Універсальна)","Головна","Бібліотека","Для вас","Альбоми","Виконавці","Енергія","Рекомендації для вас","Пошук пісень, виконавців...","Пошук","Огляд за жанрами","Усі","Завантаження","Плейлисти","Вподобані","Ваші плейлисти","пісень","Тема додатка","Світла","Темна","Система","Колір акценту","Готово","Щотижневі відкриття","Мікс улюблених треків і новинок","Мікс дня","Поп, електроніка та інді","Популярні альбоми","Найпопулярніші новинки","Рекомендовані виконавці","Відкривайте нових виконавців","Енергія та танці","Активні ритми на весь день",listOf("Танцювальна","Поп","Рок","Альтернатива","Хіп-хоп","Електроніка","Реггетон","Інді"),"Простий музичний плеєр YouTube для Android на основі Material Design, створений за допомогою Kotlin та Jetpack Compose.","Версія","Універсальна","Визначити систему","Прийняти")
        lang.startsWith("nl") -> UIStrings("Instellingen","Google-account","Snelle schakelaars","Al het andere","Locatie","App-taal","Selecteer je taal (25 talen beschikbaar)","Uiterlijk","Licht/Donker thema en accentkleuren","Cache & Audio","Gebruikt","Opslag & Opschonen","Tijdelijke bestanden en cache wissen","Over","Injector V1.0.0 (Universeel)","Start","Bibliotheek","Voor jou","Albums","Artiesten","Energie","Aanbevelingen voor jou","Zoek nummers, artiesten...","Zoeken","Ontdek op genre","Alles","Downloads","Afspeellijsten","Geliket","Jouw afspeellijsten","nummers","App-thema","Licht","Donker","Systeem","Accentkleur","Klaar","Weekly Discovery","Mix van je favorieten en nieuwe releases","Daily Mix","Pop, Electronic en Indie","Populaire albums","Meest beluisterde nieuwe releases","Aanbevolen artiesten","Ontdek nieuwe artiesten","Energie & Dance","Opzwepende ritmes voor je dag",listOf("Dance","Pop","Rock","Alternatief","Hip-Hop","Electronic","Reggaeton","Indie"),"Een eenvoudige YouTube-muziekspeler voor Android, gebaseerd op Material Design, gemaakt met Kotlin en Jetpack Compose.","Versie","Universeel","Systeem detecteren","Accepteren")
        lang.startsWith("sv") -> UIStrings("Inställningar","Google-konto","Snabbväxlar","Allt annat","Plats","Appspråk","Välj ditt språk (25 språk tillgängliga)","Utseende","Ljust/Mörkt tema och accentfärger","Cache & Ljud","Använt","Lagring & Rensning","Rensa temporära filer och cache","Om","Injector V1.0.0 (Universell)","Hem","Bibliotek","För dig","Album","Artister","Energi","Rekommendationer för dig","Sök låtar, artister...","Sök","Utforska efter genre","Alla","Nedladdningar","Spellistor","Gillade","Dina spellistor","låtar","Apptema","Ljust","Mörkt","System","Accentfärg","Klar","Veckans upptäckter","Mix av dina favoriter och nyheter","Daily Mix","Pop, Electronic och Indie","Populära album","Mest lyssnade nya släpp","Rekommenderade artister","Upptäck nya artister","Energi & Dance","Pigga rytmer för dagen",listOf("Dance","Pop","Rock","Alternativ","Hip-Hop","Electronic","Reggaeton","Indie"),"En enkel YouTube-musikspelare för Android, baserad på Material Design, skapad med Kotlin och Jetpack Compose.","Version","Universell","Identifiera system","Acceptera")
        lang.startsWith("cs") -> UIStrings("Nastavení","Účet Google","Rychlé přepínače","Vše ostatní","Umístění","Jazyk aplikace","Vyberte jazyk (k dispozici 25 jazyků)","Vzhled","Světlý/tmavý motiv a barvy zvýraznění","Mezipaměť a zvuk","Využito","Úložiště a čištění","Vymazat dočasné soubory a mezipaměť","O aplikaci","Injector V1.0.0 (Univerzální)","Domů","Knihovna","Pro vás","Alba","Umělci","Energie","Doporučení pro vás","Hledat skladby, umělce...","Hledat","Procházet podle žánru","Vše","Stažené","Playlisty","Oblíbené","Vaše playlisty","skladeb","Motiv aplikace","Světlý","Tmavý","Systém","Barva zvýraznění","Hotovo","Týdenní objevy","Mix vašich oblíbených a novinek","Denní mix","Pop, elektronika a indie","Trendy alba","Nejposlouchanější novinky","Doporučení umělci","Objevte nové umělce","Energie a tanec","Živé rytmy pro váš den",listOf("Dance","Pop","Rock","Alternativní","Hip-Hop","Elektronika","Reggaeton","Indie"),"Jednoduchý hudební přehrávač YouTube pro Android založený na Material Design, vytvořený v Kotlinu a Jetpack Compose.","Verze","Univerzální","Detekovat systém","Přijmout")
        lang.startsWith("el") -> UIStrings("Ρυθμίσεις","Λογαριασμός Google","Γρήγοροι διακόπτες","Όλα τα άλλα","Τοποθεσία","Γλώσσα εφαρμογής","Επιλέξτε τη γλώσσα σας (25 διαθέσιμες γλώσσες)","Εμφάνιση","Φωτεινό/Σκούρο θέμα και χρώματα έμφασης","Προσωρινή μνήμη & Ήχος","Χρησιμοποιημένα","Αποθήκευση & Εκκαθάριση","Εκκαθάριση προσωρινών αρχείων και cache","Σχετικά","Injector V1.0.0 (Καθολική)","Αρχική","Βιβλιοθήκη","Για εσάς","Άλμπουμ","Καλλιτέχνες","Ενέργεια","Προτάσεις για εσάς","Αναζήτηση τραγουδιών, καλλιτεχνών...","Αναζήτηση","Εξερεύνηση ανά είδος","Όλα","Λήψεις","Λίστες αναπαραγωγής","Αγαπημένα","Οι λίστες σας","τραγούδια","Θέμα εφαρμογής","Φωτεινό","Σκούρο","Σύστημα","Χρώμα έμφασης","Τέλος","Εβδομαδιαία ανακάλυψη","Μίξη αγαπημένων και νέων κυκλοφοριών","Daily Mix","Pop, Electronic και Indie","Δημοφιλή άλμπουμ","Οι πιο δημοφιλείς νέες κυκλοφορίες","Προτεινόμενοι καλλιτέχνες","Ανακαλύψτε νέους καλλιτέχνες","Ενέργεια & Dance","Ζωντανοί ρυθμοί για την ημέρα σας",listOf("Dance","Pop","Rock","Εναλλακτική","Hip-Hop","Electronic","Reggaeton","Indie"),"Ένα απλό πρόγραμμα αναπαραγωγής μουσικής YouTube για Android, βασισμένο στο Material Design, δημιουργημένο με Kotlin και Jetpack Compose.","Έκδοση","Καθολική","Εντοπισμός συστήματος","Αποδοχή")
        lang.startsWith("iw") || lang.startsWith("he") -> UIStrings("הגדרות","חשבון Google","מתגים מהירים","כל השאר","מיקום","שפת האפליקציה","בחר את השפה שלך (25 שפות זמינות)","מראה ותחושה","ערכת נושא בהירה/כהה וצבעי הדגשה","מטמון ושמע","בשימוש","אחסון וניקוי","נקה קבצים זמניים ומטמון","אודות","Injector V1.0.0 (אוניברסלי)","בית","ספרייה","בשבילך","אלבומים","אמנים","אנרגיה","המלצות בשבילך","חפש שירים, אמנים...","חיפוש","גלה לפי ז׳אנר","הכל","הורדות","פלייליסטים","אהבתי","הפלייליסטים שלך","שירים","ערכת נושא","בהיר","כהה","מערכת","צבע הדגשה","סיום","גילוי שבועי","מיקס של מועדפים ושירים חדשים","מיקס יומי","פופ, אלקטרוני ואינדי","אלבומים חמים","השירים החדשים המושמעים ביותר","אמנים מומלצים","גלה אמנים חדשים","אנרגיה ודאנס","מקצבים אנרגטיים ליום שלך",listOf("דאנס","פופ","רוק","אלטרנטיבי","היפ-הופ","אלקטרוני","רגאטון","אינדי"),"נגן מוזיקה פשוט של YouTube עבור Android, מבוסס Material Design, שנוצר עם Kotlin ו-Jetpack Compose.","גרסה","אוניברסלי","זהה מערכת","אישור")
        lang.startsWith("ro") -> UIStrings("Setări","Cont Google","Comutatoare rapide","Tot restul","Locație","Limba aplicației","Selectează limba (25 de limbi disponibile)","Aspect","Temă Luminoasă/Întunecată și culori de accent","Cache și Audio","Utilizat","Stocare și curățare","Curăță fișierele temporare și cache-ul","Despre","Injector V1.0.0 (Universal)","Acasă","Bibliotecă","Pentru tine","Albume","Artiști","Energie","Recomandări pentru tine","Caută melodii, artiști...","Caută","Explorează după gen","Toate","Descărcări","Playlisturi","Apreciate","Playlisturile tale","melodii","Tema aplicației","Luminos","Întunecat","Sistem","Culoare de accent","Gata","Descoperire săptămânală","Mix cu favoritele și noutățile tale","Daily Mix","Pop, Electronic și Indie","Albume populare","Cele mai ascultate lansări noi","Artiști recomandați","Descoperă artiști noi","Energie și Dance","Ritmuri active pentru ziua ta",listOf("Dance","Pop","Rock","Alternativ","Hip-Hop","Electronic","Reggaeton","Indie"),"Un player muzical YouTube simplu pentru Android, bazat pe Material Design, creat cu Kotlin și Jetpack Compose.","Versiune","Universal","Detectează sistemul","Acceptă")
        lang.startsWith("hu") -> UIStrings("Beállítások","Google-fiók","Gyorskapcsolók","Minden más","Hely","Alkalmazás nyelve","Válassza ki a nyelvet (25 nyelv elérhető)","Megjelenés","Világos/Sötét téma és kiemelő színek","Gyorsítótár és hang","Felhasználva","Tárhely és tisztítás","Ideiglenes fájlok és gyorsítótár törlése","Névjegy","Injector V1.0.0 (Univerzális)","Kezdőlap","Könyvtár","Neked","Albumok","Előadók","Energia","Ajánlások neked","Dalok, előadók keresése...","Keresés","Felfedezés műfaj szerint","Összes","Letöltések","Lejátszási listák","Kedvelt","Lejátszási listáid","dal","Alkalmazás témája","Világos","Sötét","Rendszer","Kiemelő szín","Kész","Heti felfedezés","Kedvencek és újdonságok mixe","Napi mix","Pop, elektronikus és indie","Felkapott albumok","Legtöbbet hallgatott újdonságok","Ajánlott előadók","Fedezz fel új előadókat","Energia és Dance","Pörgős ritmusok a napodhoz",listOf("Dance","Pop","Rock","Alternatív","Hip-Hop","Elektronikus","Reggaeton","Indie"),"Egyszerű YouTube zenelejátszó Androidra, Material Design alapon, Kotlin és Jetpack Compose használatával.","Verzió","Univerzális","Rendszer észlelése","Elfogadás")
        else -> UIStrings(
            "Settings","Google Account","Quick toggles","Everything else","Location","App language",
            "Select your language (25 languages available)","Look & Feel","Light/Dark theme and accent colors",
            "Cache & Audio","Used","Storage & Cleanup","Clean temporary files and cache",
            "About","Injector V1.0.0 (Universal)","Home","Library","For you","Albums","Artists","Energy",
            "Recommendations for you","Search songs, artists...","Search","Explore by genre",
            "All","Downloads","Playlists","Liked","Your playlists","songs",
            "App theme","Light","Dark","System","Accent color","Done",
            "Weekly Discovery","Mix of your favorites and new releases",
            "Daily Mix","Pop, Electronic and Indie",
            "Trending Albums","Most listened new releases",
            "Recommended Artists","Discover new artists",
            "Energy & Dance","Upbeat rhythms for your day",
            listOf("Dance","Pop","Rock","Alternative","Hip-Hop","Electronic","Reggaeton","Indie"),"A simple YouTube music player for Android, based on Material Design, built with Kotlin and Jetpack Compose.","Version","Universal","Detect System","Accept"
        )
    }
}
