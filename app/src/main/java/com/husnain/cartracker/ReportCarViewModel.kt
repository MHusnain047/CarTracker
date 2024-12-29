package com.husnain.cartracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReportCarViewModel:ViewModel() {
    val CarsRepository = CarsRepository()
    val isSaving = MutableStateFlow<Boolean>(false)
    val isSaved= MutableStateFlow<Boolean?>(null)
    val failureMessage = MutableStateFlow<String?>(null)


//    fun uploadImageAndSaveLost(imagePath: String, lost: Lost) {
//        storageRepository.uploadFile(imagePath, onComplete = { success, result ->
//            if (success) {
//                lost.image=result!!
//                saveLost(lost)
//            }
//            else failureMessage.value=result
//        })
//    }
    fun savedetail(carDetails: CarDetails) {
        viewModelScope.launch {
            val result = CarsRepository.saveDetail(carDetails)
            if (result.isSuccess) {
                isSaving.value = true
            } else {
                failureMessage.value = result.exceptionOrNull()?.message
            }
        }
    }


}