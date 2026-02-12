import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.github.terrakok.mobicon.App
import com.github.terrakok.mobicon.initKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialize Koin first
    initKoin()
    
    // Get DeeplinkService from Koin and set the initial deep link
    //val deeplinkService = KoinPlatform.getKoin().get<DeeplinkService>()
    //deeplinkService.setDeepLink(window.location.toString())
    
    ComposeViewport { App() }
}
