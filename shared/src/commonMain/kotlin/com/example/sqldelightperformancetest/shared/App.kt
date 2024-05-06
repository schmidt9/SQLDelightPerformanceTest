import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.sqldelightperformancetest.shared.HomeScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Napier.base(DebugAntilog())

    MaterialTheme {
        Navigator(screen = HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}