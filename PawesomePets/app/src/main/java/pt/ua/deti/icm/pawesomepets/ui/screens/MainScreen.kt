package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import pt.ua.deti.icm.pawesomepets.ui.theme.PawesomePetsTheme
import java.math.BigDecimal
import kotlin.random.Random

@Composable
fun MainScreen(user: String) {
    Column {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Olá, $user")
        }
    }

}

@Preview
@Composable
fun MainScreenPreview() {
    PawesomePetsTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen("mariana")
        }
    }
}