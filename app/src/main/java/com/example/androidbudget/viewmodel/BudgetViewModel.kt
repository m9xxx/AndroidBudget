package com.example.androidbudget.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbudget.data.Budget
import com.example.androidbudget.data.Expense
import com.example.androidbudget.repository.BudgetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BudgetState(
    val budget: Budget = Budget(),
    val remainingDays: Int = 0
)

class BudgetViewModel(private val repository: BudgetRepository = BudgetRepository()) : ViewModel() {
    val budgetState = repository.budgetState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Budget()
    )

    fun calculateDailyBudget(totalBudget: Double, days: Int) {
        viewModelScope.launch {
            repository.calculateDailyBudget(Budget(totalBudget, days))
        }
    }

    fun addExpense(amount: Double) {
        viewModelScope.launch {
            repository.addExpense(Expense(amount))
        }
    }

    fun nextDay() {
        viewModelScope.launch {
            repository.nextDay()
        }
    }

    fun getRemainingDays(): Int = repository.getRemainingDays()
    fun getDailyBudget(): Double = repository.getDailyBudget()
    fun getCurrentDayBudget(): Double = repository.getCurrentDayBudget()
    fun getRemainingTotalBudget(): Double = repository.getRemainingTotalBudget()
}