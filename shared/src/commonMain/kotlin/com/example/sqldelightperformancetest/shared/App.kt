import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.example.sqldelightperformancetest.shared.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(context: Any? = null) {
    MaterialTheme {
        Navigator(screen = HomeScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
