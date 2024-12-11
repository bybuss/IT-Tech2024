package bob.colbaskin.hackatontemplate.home.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.UUID

@Composable
fun HomeScreen(
    onDetailedClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val strategies by viewModel.strategies.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "СТРАТЕГИЧЕСКИЙ ОФИС",
                    fontSize = 40.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
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
                                StrategyCard(
                                    id = strategy.id,
                                    endDate = strategy.endDate,
                                    sum = strategy.sum,
                                    title = "",
                                    onClick = { },
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
fun StrategyCard(
    id: UUID,
    title: String,
    endDate: String,
    sum: Int,
    onClick: () -> Unit,
    onDetailedClick: (String) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = id.toString().take(25) + "...",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Скопировать",
                    tint = MaterialTheme.colorScheme.scrim,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            clipboardManager.setText(androidx.compose.ui.text.AnnotatedString(id.toString()))
                            Toast.makeText(context, "ID скопирован: $id", Toast.LENGTH_SHORT).show()
                        }
                )
                Spacer(modifier = Modifier.weight(0.9f))
                Text(
                    text = endDate,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Row () {
                    Text(
                        text = "Остаток:",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.scrim
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1 ₽",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFF9C27B0), RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "SIm",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "$sum ₽",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.scrim,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "В процессе...",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Перейти",
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.size(32.dp).clickable {
                            onDetailedClick(id.toString())
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(onDetailedClick = {})
}

@Preview
@Composable
fun StrategyCardPreview() {
    StrategyCard(
        id = UUID.randomUUID(),
        title = "Стратегия 1",
        sum = 100000,
        onClick = {},
        endDate = "123.03.2021",
        onDetailedClick = {}
    )
}