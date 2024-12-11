package bob.colbaskin.hackatontemplate.profile.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import bob.colbaskin.hackatontemplate.profile.domain.models.SimpleStrategy
import java.util.UUID

@Composable
fun ProfileScreen(
    onDetailedClick: (String) -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val strategies: List<SimpleStrategy> by viewModel.strategies.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            contentAlignment = Alignment.Center
        ) {
            ProfileHeader(
                onAccountExitClick = { viewModel.accountExit() }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.Black
                )
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(strategies) { strategy ->
                                HistoryCard(
                                    id = strategy.id,
                                    startDate = strategy.startDate,
                                    endDate = strategy.endDate,
                                    sum = strategy.sum,
                                    onDetailedClick = { id ->
                                        onDetailedClick(id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryCard(
    id: UUID,
    startDate: String,
    endDate: String,
    sum: Int,
    onDetailedClick: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = id.toString().take(25) + "...",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.weight(0.1f))

                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Скопировать",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(id.toString()))
                            Toast.makeText(context, "ID скопирован: $id", Toast.LENGTH_SHORT).show()
                        }
                )

                Spacer(modifier = Modifier.weight(0.9f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Перейти",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(24.dp).clickable {
                        onDetailedClick(id.toString())
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Дата: с $startDate по $endDate",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Сумма вложений: $sum ₽",
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = MaterialTheme.colorScheme.surface,
            )
        }
    }
}

@Composable
fun ProfileHeader(
    onAccountExitClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onAccountExitClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 8.dp, vertical = 32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Output,
                contentDescription = "Выход",
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Person,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(50.dp)
                    )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "test@gmail.com",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen {}
}

@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader({})
}

@Preview(showBackground = true)
@Composable
fun HistoryCardPreview() {
    HistoryCard(
        id = UUID.randomUUID(),
        startDate = "01.01.2000",
        endDate = "01.01.2001",
        sum = 2000000,
        onDetailedClick = {}
    )
}