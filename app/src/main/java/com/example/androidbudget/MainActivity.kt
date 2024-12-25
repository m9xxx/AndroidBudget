package com.example.androidbudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.androidbudget.ui.BudgetScreen // Импортируем BudgetScreen
import com.example.androidbudget.ui.theme.AndroidBudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBudgetTheme {
                BudgetScreen() // Запускаем BudgetScreen
            }
        }
    }
}