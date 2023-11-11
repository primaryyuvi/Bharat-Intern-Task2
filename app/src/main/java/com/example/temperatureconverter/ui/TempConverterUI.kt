package com.example.temperatureconverter.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.temperatureconverter.R
import com.example.temperatureconverter.TempViewModel
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

@SuppressLint("ResourceType")
@Composable
fun TemperatureConvertorLayout(tempviewmodel : TempViewModel = viewModel()){
        val tempUiState by tempviewmodel.uiState.collectAsState()
        var tInput by remember {tempviewmodel.tmpInput}
        var selectedInput by remember { tempviewmodel.sInput }
    Column (modifier = Modifier.paint(painterResource(id = R.xml.red_back))){
            InputLayout(valueInput = tInput,
                selected = selectedInput ,
                onButtonClick = {
                    selectedInput = !selectedInput
                    tempviewmodel.selectedChange(selectedInput)
                },
                onValueChange = {
                    tInput = it
                    tempviewmodel.inputChanged(it)
                }
            )
        Spacer(modifier = Modifier.height(150.dp))
            OutputLayout(answer = tempviewmodel.converted, converted = tempUiState.selected)
        }
}

@SuppressLint("ResourceType")
@Composable
fun InputLayout(valueInput : String, selected : Boolean, onValueChange: (String) -> Unit, onButtonClick : () ->Unit ){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
        Text(
            text = "Temperature converter",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            fontFamily = FontFamily.SansSerif

        )
                ConversionDropDown(selected = selected,onButtonClick = onButtonClick)
                InputTxt(value = valueInput, onValueChange = onValueChange)

            }
}

@Composable
fun ConversionDropDown(selected: Boolean, onButtonClick: () -> Unit)  {
    Card (colors = CardDefaults.cardColors(
        containerColor = Color.Transparent,
        contentColor = Color.White
    )){
        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .padding(bottom = 64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = contentColorFor(Color.White)
            )

        ) {
            if (selected) {
                Text(text = "CELSIUS",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif)

            } else {
                Text(text = "FAHRENHEIT",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif)
            }
        }
    }
}

@Composable
fun OutputTxt(text : Double){
        val textToString = text.toString()
        Text(
            text = stringResource(id = R.string.Answer,textToString),
            fontWeight = Bold,
            modifier = Modifier.padding(bottom = 40.dp),
            fontSize = 64.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Red
        )
}
@Composable
fun OutputLayout(answer : Double,converted : Boolean){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)){
            OutputTxt(answer)
            if(!converted){
                ConvertedText(convertedText = "CELSIUS")
            }
            else{
                ConvertedText(convertedText = "FAHRENHEIT")
            }
        }
    }
@Composable
fun ConvertedText(convertedText : String){
        Text(text = convertedText,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            color = Color.Red
            )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTxt(
    value : String,
    onValueChange : (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .background(Color.Transparent)
            .size(150.dp)
            .height(32.dp)
            .padding(bottom = 32.dp),
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        textStyle = LocalTextStyle.current.copy(fontWeight = Bold,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontFamily = FontFamily.SansSerif),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.White,
            textColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        placeholder = { Text(
            text = "0.0",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            color = Color.White,
            fontWeight = Bold,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(start = 24.dp)
            )
        }

    )
}
@Preview(showBackground = true)
@Composable
fun Preview(){
    TemperatureConverterTheme(darkTheme = true) {
        TemperatureConvertorLayout()
    }
}