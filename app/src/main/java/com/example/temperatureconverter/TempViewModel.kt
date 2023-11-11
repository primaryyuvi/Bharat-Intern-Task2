package com.example.temperatureconverter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.temperatureconverter.Data.TempUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import java.math.RoundingMode

class TempViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TempUiState())
    val uiState: StateFlow<TempUiState> = _uiState.asStateFlow()
    var tmpInput = mutableStateOf("")
    var sInput = mutableStateOf(true)


    fun inputChanged(newValue : String){
        _uiState.value.tempValue = newValue
        updateConversion()
    }


    fun selectedChange(selectedValue : Boolean){
        _uiState.value.selected = selectedValue
        updateConversion()
    }

    var converted : Double = 0.0

    private fun updateConversion() {
        val temp = _uiState.value.tempValue.toDoubleOrNull() ?: 0.0
        converted = if (_uiState.value.selected)
            conversionOfFahrenheit(temp)
        else
            conversionOfCelsius(temp)
    }

    private fun conversionOfCelsius(temp : Double) : Double{
        val number = (temp - 32.0) * 5.0 / 9.0
        val bd = BigDecimal(number).setScale(3, RoundingMode.HALF_EVEN)
        return bd.toDouble()
    }

    private fun conversionOfFahrenheit(temp : Double) : Double{
        val number = (temp * (9.0 / 5.0 ) ) + 32
        val bd = BigDecimal(number).setScale(3, RoundingMode.HALF_EVEN)
        return bd.toDouble()
    }

}