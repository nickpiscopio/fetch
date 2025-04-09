import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainCompose() {
    Box(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        FetchCompose()
    }
}