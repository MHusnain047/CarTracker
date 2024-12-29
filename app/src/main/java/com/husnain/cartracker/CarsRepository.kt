package com.husnain.cartracker

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CarsRepository {
    private val carsCollection = FirebaseFirestore.getInstance().collection("carsCollection")

    suspend fun saveDetail(cars: CarDetails): Result<Boolean> {
        return try {
            val document = carsCollection.document()
            cars.id = document.id
            document.set(cars).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateRequest(cars: CarDetails): Result<Boolean> {
        return try {
            if (cars.userID.isNullOrEmpty()) {
                return Result.failure(IllegalArgumentException("UserID is null or empty"))
            }
            // Update the document with the provided UserID
            carsCollection.document(cars.userID!!).set(cars).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDetail(cars: CarDetails): Result<Boolean> {
        return try {
            val document = carsCollection.document(cars.id)
            document.set(cars).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCase(reportID: String): Result<Boolean> {
        return try {
            carsCollection.document(reportID).delete().await()
            Log.d(TAG, "deleteLost: Detail deleted: $reportID")
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG, "deleteLost: Failed to delete detail: $reportID", e)
            Result.failure(e)
        }
    }

    fun getCars() =
        carsCollection.snapshots().map { it.toObjects(CarDetails::class.java) }

    fun getlostCars() =
        carsCollection.whereEqualTo("lost", false) // Query cars where status is "lost"
            .snapshots()
            .map { it.toObjects(CarDetails::class.java) }

    fun getfoundCars() =
        carsCollection.whereEqualTo("found", true) // Query cars where status is "found"
            .snapshots()
            .map { it.toObjects(CarDetails::class.java) }
}
