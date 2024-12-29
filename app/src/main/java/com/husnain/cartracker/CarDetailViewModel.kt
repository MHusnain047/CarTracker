package com.husnain.cartracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarDetailViewModel : ViewModel() {

    private val CarsRepository = CarsRepository()

    // StateFlows to observe update and delete operations
    val isUpdated = MutableStateFlow<Boolean?>(null)
    val isDeleted = MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)

    // Function to update the case
    fun updatedetail(carDetails: CarDetails) {
        viewModelScope.launch {
            val result = CarsRepository.updateDetail(carDetails)
            if (result.isSuccess) {
                isUpdated.value = true
            } else {
                failureMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

    // Function to delete the case
    fun deleteCase(caseId: String) {
        viewModelScope.launch {
            try {
                val result = CarsRepository.deleteCase(caseId)
                if (result.isSuccess) {
                    isDeleted.value = true // Deletion was successful
                } else {
                    failureMessage.value = result.exceptionOrNull()?.message
                }
            } catch (e: Exception) {
                failureMessage.value = e.message // Handle unexpected errors
            }
        }
    }
}