import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.github.terrakok.mobicon.App
import com.github.terrakok.mobicon.DeeplinkService
import com.github.terrakok.mobicon.initKoin
import java.awt.Dimension

fun main() = application {
    // Initialize Koin
    initKoin()
    
    Window(
        title = "MobiCon",
        state = rememberWindowState(width = 500.dp, height = 900.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(500, 900)
        App()
    }
}
