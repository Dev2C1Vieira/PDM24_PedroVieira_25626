package com.pedro.carmuseum.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>
}
