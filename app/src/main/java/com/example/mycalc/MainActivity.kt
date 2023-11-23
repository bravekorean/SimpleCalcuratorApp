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


        // 모든 숫자 및 연산자 버튼에 대한 클릭 리스너 설정
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnEquals, R.id.btnClear
        )

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
            else -> appendToInput(buttonText)
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

