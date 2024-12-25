package com.example.androidbudget.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidbudget.viewmodel.BudgetViewModel
import com.example.androidbudget.viewmodel.BudgetState
import kotlin.compareTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(viewModel: BudgetViewModel = viewModel()) {
    val budgetState by viewModel.budgetState.collectAsState()
    var budgetInput by remember { mutableStateOf("") }
    var daysInput by remember { mutableStateOf("") }
    var expenseInput by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Контроль Бюджета",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            if (budgetState.totalBudget == 0.0) {
                OutlinedTextField(
                    value = budgetInput,
                    onValueChange = { budgetInput = it },
                    label = { Text("Введите общий бюджет") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = daysInput,
                    onValueChange = { daysInput = it },
                    label = { Text("Введите количество дней") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val budget = budgetInput.toDoubleOrNull() ?: 0.0
                        val days = daysInput.toIntOrNull() ?: 0
                        viewModel.calculateDailyBudget(budget, days)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Рассчитать бюджет",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                Text("Дневной бюджет: ${String.format("%.2f ₽", viewModel.getDailyBudget())}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("Остаток на сегодня: ${String.format("%.2f ₽", viewModel.getCurrentDayBudget())}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("Осталось дней: ${viewModel.getRemainingDays()}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("Общий остаток: ${String.format("%.2f ₽", viewModel.getRemainingTotalBudget())}")

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = expenseInput,
                    onValueChange = { expenseInput = it },
                    label = { Text("Введите расход") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val expense = expenseInput.toDoubleOrNull() ?: 0.0
                            viewModel.addExpense(expense)
                            expenseInput = ""
                        }
                    ) {
                        Text("Добавить расход")
                    }

                    Button(
                        onClick = { viewModel.nextDay() },
                        enabled = viewModel.getRemainingDays() > 1
                    ) {
                        Text("Следующий день")
                    }
                }
            }
        }
    }
}