package org.rriv.android.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class GraphParameterState(
    val conductivity: Boolean = true,
    val temperature: Boolean = true,
    val ch4: Boolean = false,
    val co2: Boolean = false,
    val ph: Boolean = false,
)

class OutputScreenViewModel : ViewModel() {
    private val _state = MutableLiveData(GraphParameterState())
    val state: LiveData<GraphParameterState> = _state

    fun updateConductivity(isChecked: Boolean){
        _state.value = _state.value?.copy(conductivity = isChecked )
    }

    fun updateTemperature(isChecked: Boolean){
        _state.value = _state.value?.copy(temperature = isChecked )
    }

    fun updateCo2(isChecked: Boolean){
        _state.value = _state.value?.copy(co2 = isChecked )
    }

    fun updateCh4(isChecked: Boolean){
        _state.value = _state.value?.copy(ch4 = isChecked )
    }

    fun updatePh(isChecked: Boolean){
        _state.value = _state.value?.copy(ph = isChecked )
    }

}