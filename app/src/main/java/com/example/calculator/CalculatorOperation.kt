package com.example.calculator

sealed class CalculatorOperation(symbol: String) {
    abstract val symbol: String?

    object Add: CalculatorOperation("+")
    object Subtract: CalculatorOperation("-")
    object Multiply: CalculatorOperation("x")
    object Divide: CalculatorOperation("/")
}