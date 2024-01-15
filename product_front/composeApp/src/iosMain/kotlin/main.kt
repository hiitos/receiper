import androidx.compose.ui.window.ComposeUIViewController
import org.kakazuto.receiper.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
