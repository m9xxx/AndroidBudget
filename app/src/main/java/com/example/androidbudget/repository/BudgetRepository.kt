package com.example.androidbudget.repository

import com.example.androidbudget.data.Budget
import com.example.androidbudget.data.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.compareTo
import kotlin.div

class BudgetRepository {
    private val _budgetState = MutableStateFlow(Budget())
    val budgetState: StateFlow<Budget> = _budgetState

    private var remainingDays: Int = 0
    private var dailyBudget: Double = 0.0
    private var currentDayBudget: Double = 0.0
    private var remainingTotalBudget: Double = 0.0

    fun calculateDailyBudget(budget: Budget) {
        dailyBudget = if (budget.numberOfDays > 0) budget.totalBudget / budget.numberOfDays else 0.0
        remainingDays = budget.numberOfDays
        currentDayBudget = dailyBudget
        remainingTotalBudget = budget.totalBudget
        _budgetState.value = budget
    }

    fun addExpense(expense: Expense) {
        currentDayBudget -= expense.amount
        remainingTotalBudget -= expense.amount
    }

    fun nextDay() {
        remainingDays--
        val savedOrOverspent = currentDayBudget
        remainingTotalBudget += savedOrOverspent
        dailyBudget = if (remainingDays > 0) remainingTotalBudget / remainingDays else 0.0
        currentDayBudget = dailyBudget
    }


    fun getRemainingDays(): Int = remainingDays
    fun getDailyBudget(): Double = dailyBudget
    fun getCurrentDayBudget(): Double = currentDayBudget
    fun getRemainingTotalBudget(): Double = remainingTotalBudget
}