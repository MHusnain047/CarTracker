package com.husnain.cartracker


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TrackedFragmentViewModel: ViewModel() {

    val carsRepository = CarsRepository()

    val failureMessage = MutableStateFlow<String?>(null)
    val data = MutableStateFlow<List<CarDetails>?>(null)

    init {
        readFoundCar()
    }


    fun readFoundCar( ) {
        viewModelScope.launch {
            carsRepository.getfoundCars()
                .catch { exception ->
                    failureMessage.value = exception.message // Handle errors gracefully
                }
                .collect { itemList ->
                    data.value = itemList // Update the live data with the retrieved items
                }
        }
    }

}