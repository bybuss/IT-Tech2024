package bob.colbaskin.hackatontemplate.analytics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnalyticsScreen(
    onClick: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val date by viewModel.date.collectAsState()
    val budget by viewModel.budget.collectAsState()

    Column (
        modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateStrategyElement(
            title = "Дайте название \n своей стратегии",
            description = "Заполните поле дав название своему\n плану, который перевернёт \n этот мир",
            textField = {
                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.setName(it) },
                    label = { Text("Введите название") }
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CreateStrategyElement(
            title = "Временной период",
            description = "Напишите дату, когда требуется\n закончить стратегию.",
            textField = {
                OutlinedTextField(
                    value = date,
                    onValueChange = { viewModel.setDate(it) },
                    label = { Text("__.__.____") }
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CreateStrategyElement(
            title = "Сумма вложений",
            description = "Введите сумму ваших вложений \n для вашей стратегии.",
            textField = {
                OutlinedTextField(
                    value = budget,
                    onValueChange = { viewModel.setBudget(it) },
                    label = { Text("Сумма вложений... ₽") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onClick,
            modifier = Modifier,
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Начать стратегию")
                Spacer(modifier = Modifier.width(32.dp))
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Начать стратегию"
                )
            }
        }
    }
}

@Composable
fun CreateStrategyElement(
    title: String,
    description: String,
    textField: @Composable () -> Unit
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        textField()
    }
}

@Preview(showBackground = true)
@Composable
fun CreateStrategyElementPreview() {
    CreateStrategyElement(
        title = "Дайте название \n своей стратегии",
        description = "Заполните поле дав название своему \n плану, который перевернёт \n этот мир",
        textField = {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                label = { Text("Введите название") }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AnalyticsScreenPreview() {
    AnalyticsScreen(
        onClick = {}
    )
}