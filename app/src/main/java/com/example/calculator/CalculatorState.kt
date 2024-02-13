package com.example.calculator

data class CalculatorState(
    val inputs: MutableList<Int>? = null,
    val number1: String,
    val number2: String,
    val operations: CalculatorOperation? = null
) {

}
