package com.husnain.cartracker

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.husnain.cartracker.MainActivity

    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.catch
    import kotlinx.coroutines.launch

    class HomeFragmentViewModel : ViewModel() {
        val orderRepository = CarsRepository()

        val failureMessage = MutableStateFlow<String?>(null)
        val data = MutableStateFlow<List<CarDetails>?>(null)

        init {
        readOrders()

        }


        fun readOrders() {
            viewModelScope.launch {
                orderRepository.getCars().catch {
                    failureMessage.value = it.message
                }
                    .collect {
                        data.value = it
                    }
            }
        }

}