package com.pedro.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator()
        }
    }
}

@Composable
fun Calculator() {
    var displayText by remember { mutableStateOf("") }
    var storedValue by remember { mutableDoubleStateOf(0.0) }
    var currentOperation by remember { mutableStateOf<Operation?>(null) }
    var memoryValue by remember { mutableDoubleStateOf(0.0) }

    val maxLength = 15

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Here is the calculator structure
        Box(
            modifier = Modifier
                .size(300.dp, 500.dp)
                .align(Alignment.Center)
                .background(Color(0xFF767676))
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
        ) {
            // Content of the calculator (eg: Buttons, Screen, etc)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                CalculatorScreen(displayText = displayText)
                CallButtons(onButtonClick = { buttonText ->
                    when (buttonText) {
                        "ON/C" -> {
                            displayText = ""
                            storedValue = 0.0
                            currentOperation = null
                        }
                        "CE" -> {
                            displayText = ""
                            storedValue = 0.0
                            currentOperation = null
                        }
                        "=" -> {
                            if (currentOperation != null && displayText.isNotEmpty()) {
                                val secondValue = displayText.toDoubleOrNull() ?: 0.0
                                val result = performOperation(storedValue, secondValue, currentOperation!!)
                                displayText = result.toString()
                                currentOperation = null
                            }
                        }
                        "+", "-", "x", "/" -> {
                            if (displayText.isNotEmpty()) {
                                storedValue = displayText.toDoubleOrNull() ?: 0.0
                                currentOperation = Operation.fromString(buttonText)
                                displayText = ""
                            }
                        }
                        "MRC" -> {
                            if (displayText == memoryValue.toString()) {
                                memoryValue = 0.0  // Clear memory if MRC pressed twice
                            } else {
                                displayText = memoryValue.toString()  // Recall memory
                            }
                        }
                        "M-" -> {
                            memoryValue -= displayText.toDouble()
                            displayText = ""  // Clear display after memory subtraction
                        }
                        "M+" -> {
                            memoryValue += displayText.toDouble()
                            displayText = ""  // Clear display after memory addition
                        }
                        "√" -> {
                            displayText = kotlin.math.sqrt(displayText.toDouble()).toString()
                        }
                        "%" -> {
                            if (currentOperation != null) {
                                val percentage = displayText.toDouble() / 100
                                displayText = (storedValue * percentage).toString()
                            }
                        }
                        "+/-" -> {
                            displayText = (displayText.toDouble() * -1).toString()
                        }
                        else -> {
                            if (displayText.length < maxLength) {
                                displayText += buttonText
                            }
                        }
                    }
                })
            }
        }
    }
}

enum class Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE;

    companion object {
        fun fromString(symbol: String): Operation? {
            return when (symbol) {
                "+" -> ADD
                "-" -> SUBTRACT
                "x" -> MULTIPLY
                "/" -> DIVIDE
                else -> null
            }
        }
    }
}

fun performOperation(firstValue: Double, secondValue: Double, operation: Operation): Double {
    return when (operation) {
        Operation.ADD -> firstValue + secondValue
        Operation.SUBTRACT -> firstValue - secondValue
        Operation.MULTIPLY -> firstValue * secondValue
        Operation.DIVIDE -> firstValue / secondValue
    }
}

@Composable
fun CallButtons(onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            TemplateButton(onClick = { onButtonClick("MRC") }, string = "MRC", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("√") }, string = "√", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("7") }, string = "7", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("4") }, string = "4", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("1") }, string = "1", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("0") }, string = "0", Color(0xFF767676))
        }
        Column {
            TemplateButton(onClick = { onButtonClick("M-") }, string = "M-", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("%") }, string = "%", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("8") }, string = "8", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("5") }, string = "5", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("2") }, string = "2", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick(".") }, string = ".", Color(0xFF767676))
        }
        Column {
            TemplateButton(onClick = { onButtonClick("M+") }, string = "M+", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("+/-") }, string = "+/-", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("9") }, string = "9", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("6") }, string = "6", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("3") }, string = "3", Color(0xFF767676))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("=") }, string = "=", Color(0xFF767676))
        }
        Column {
            TemplateButton(onClick = { onButtonClick("ON/C") }, string = "ON/C", Color(0xFFc15c76))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("CE") }, string = "CE", Color(0xFFc15c76))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("/") }, string = "/", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("x") }, string = "x", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("-") }, string = "-", Color(0xFF2d2e2c))
            Spacer(modifier = Modifier.height(10.dp))
            TemplateButton(onClick = { onButtonClick("+") }, string = "+", Color(0xFF2d2e2c))
        }
    }
}

@Composable
fun CalculatorScreen(displayText: String) {
    Box(
        modifier = Modifier
            .size(300.dp, 70.dp)
            .height(70.dp)
            .background(Color(0xFFbbc7b5))
            .clip(RoundedCornerShape(10.dp))
            .border(5.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = displayText,
            fontSize = 30.sp,
            color = Color.Black
        )
    }
}

// Buttons Colors
// Color(0xFF767676) Gray
// Color(0xFF2d2e2c) Black
// Color(0xFFc15c76) Red

@Composable
fun TemplateButton(
    onClick: () -> Unit,
    string: String,
    backgroundColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp)
            .height(40.dp),
        // Set Button Color and Button Text Color
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color(red = 255, green = 255, blue = 255, alpha = 255)
        ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(3.dp, Color.Black),
        contentPadding = PaddingValues(0.dp)

    ) {
        Text(
            text = string,
            fontSize = 15.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun BlueBackPreview() {
    Calculator()
}

@Preview(showBackground = true)
@Composable
fun CallButtonsPreview() {
    CallButtons(onButtonClick = { buttonText -> println("Button clicked: $buttonText") })
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen( displayText = "" )
}

@Preview(showBackground = true)
@Composable
fun TemplateButtonPreview() {
    TemplateButton( onClick = { }, string = "", backgroundColor = Color.Black )
}