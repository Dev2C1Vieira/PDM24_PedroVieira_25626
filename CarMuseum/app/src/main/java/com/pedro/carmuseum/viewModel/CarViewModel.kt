import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pedro.carmuseum.data.Car
import com.pedro.carmuseum.data.CarDatabase
import com.pedro.carmuseum.repository.CarRepository
import kotlinx.coroutines.launch

class CarViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CarRepository
    val allCars: LiveData<List<Car>>

    init {
        val carDao = CarDatabase.getDatabase(application).carDao()
        repository = CarRepository(carDao)
        allCars = repository.allCars.asLiveData()
    }

    // Método para obter um carro pelo ID
    fun getCarById(id: Int): Car? {
        return allCars.value?.find { it.id == id }
    }

    fun insert(car: Car) = viewModelScope.launch {
        repository.insert(car)
    }

    fun update(car: Car) = viewModelScope.launch {
        repository.update(car)
    }

    fun delete(car: Car) = viewModelScope.launch {
        repository.delete(car)
    }
}
