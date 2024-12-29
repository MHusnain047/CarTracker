package com.husnain.cartracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ReportedFragmentViewModel: ViewModel() {

    val carsRepository = CarsRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<CarDetails>?>(null)
 init {
     readLostCar()
 }


    fun readLostCar() {
        viewModelScope.launch {
            carsRepository.getlostCars()
                .catch { exception ->
                    failureMessage.value = exception.message
                }
                .collect { itemList ->
                    data.value = itemList
                }
        }
    }

}