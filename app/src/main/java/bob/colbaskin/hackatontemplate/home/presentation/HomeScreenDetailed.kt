package bob.colbaskin.hackatontemplate.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import bob.colbaskin.hackatontemplate.navigation.TopNavBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenDetailed(
    id: String,
    onClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopNavBar(
                onClick = { onClick() },
                title = id
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Home Detailed Screen",
                textAlign = TextAlign.Center
            )
        }
    }
}