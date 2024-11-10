package com.pedro.carmuseum.repository

import com.pedro.carmuseum.data.Car
import com.pedro.carmuseum.data.CarDao
import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao) {
    val allCars: Flow<List<Car>> = carDao.getAllCars()

    suspend fun insert(car: Car) {
        carDao.insert(car)
    }

    suspend fun update(car: Car) {
        carDao.update(car)
    }

    suspend fun delete(car: Car) {
        carDao.delete(car)
    }
}
