package com.example.mycalc

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var gridLayout: GridLayout
    private var currentInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        gridLayout = findViewById(R.id.gridLayout)


        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnClear, R.id.btnPercent, R.id.btnTax
        )

// 기존의 for 루프에 퍼센트 계산 버튼에 대한 클릭 리스너 설정 추가
        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { onButtonClick(it) }
        }
    }



    private fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (button.id) {
            R.id.btnEquals -> evaluateExpression()
            R.id.btnClear -> clearInput()
            R.id.btnPercent -> appendPercentage() // 퍼센트 계산 버튼에 대한 처리 추가
            R.id.btnTax -> applyTax()
            else -> appendToInput(buttonText)
        }
    }

    private fun applyTax() {
        try {
            val value = currentInput.toDouble()
            val taxRate = 0.1 // 10% 세율
            val taxAmount = value * taxRate
            val totalAmount = value + taxAmount

            val formattedResult = if (totalAmount % 1 == 0.0) {
                totalAmount.toInt().toString() // 결과가 정수이면 정수로 변환
            } else {
                totalAmount.toString() // 아니면 그대로 유지
            }

            display.text = formattedResult
            currentInput = formattedResult
        } catch (e: Exception) {
            display.text = "오류"
            currentInput = ""
        }
    }

    // 퍼센트 계산 기능 추가
    private fun appendPercentage() {
        try {
            val value = currentInput.toDouble()
            val percentResult = value / 100
            display.text = percentResult.toString()
            currentInput = percentResult.toString()
        } catch (e: Exception) {
            display.text = "오류"
            currentInput = ""
        }
    }


    private fun appendToInput(value: String) {
        currentInput += value
        display.text = currentInput
    }

    @SuppressLint("SetTextI18n")
    private fun evaluateExpression() {
        try {
            val result = eval(currentInput)
            val formattedResult = if (result % 1 == 0.0) {
                result.toInt().toString() // 결과가 정수이면 정수로 변환
            } else {
                result.toString() // 아니면 그대로 유지
            }
            display.text = formattedResult
            currentInput = formattedResult
        } catch (e: Exception) {
            display.text = "오류"
            currentInput = ""
        }
    }

    private fun clearInput() {
        currentInput = ""
        display.text = ""
    }

    private fun eval(expression: String): Double {
        return when {
            expression.contains("+") -> {
                val numbers = expression.split("+")
                numbers[0].toDouble() + numbers[1].toDouble()
            }
            expression.contains("-") -> {
                val numbers = expression.split("-")
                numbers[0].toDouble() - numbers[1].toDouble()
            }
            expression.contains("*") -> {
                val numbers = expression.split("*")
                numbers[0].toDouble() * numbers[1].toDouble()
            }
            expression.contains("/") -> {
                val numbers = expression.split("/")
                numbers[0].toDouble() / numbers[1].toDouble()
            }
            else -> throw IllegalArgumentException("Invalid expression")
        }
    }
}


