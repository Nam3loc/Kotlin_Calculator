package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
        }
    }

    private fun enterNumber(number: Int) {
        if(state.operations == null) {
            if(state.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number1 = state.number1 + number
            )
            if(state.number2.length >= MAX_NUM_LENGTH) {
                return
            }
            state = state.copy(
                number2 = state.number2 + number
            )
        }

    }

    private fun enterDecimal() {
        if(state.operations == null && !state.number1.contains(".")
            && state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if(state.operations == null && !state.number2.contains(".")
            && state.number2.isNotBlank()) {
            state = state.copy(
                number2 = state.number2 + "."
            )
            return
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if(state.number1.isNotBlank()) {
            state = state.copy(operations = operation)
        }
    }

    private fun performCalculation() {
        val firstInput = state.number1
        val secondInput = state.number2
        if(firstInput != null && secondInput != null) {
            val result = when(state.operations) {
                is CalculatorOperation.Add -> firstInput + secondInput
                is CalculatorOperation.Subtract -> firstInput - secondInput
                is CalculatorOperation.Multiply -> firstInput * secondInput
                is CalculatorOperation.Divide -> firstInput / secondInput
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operations = null
            )
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operations != null -> state = state.copy(
                operations = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number2.dropLast(1)
            )
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}
