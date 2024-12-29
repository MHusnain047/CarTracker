package com.husnain.cartracker

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await

class AuthRepository {

    suspend fun logout():Result<Boolean>{
        FirebaseAuth.getInstance().signOut()
        return Result.success(true)
    }


    suspend fun login(email: String, password: String): Result<Users> {
        try {
            val result = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!

            val user = Users(
                id = firebaseUser.uid,
                role = "owner",
                fullName = firebaseUser.displayName ?: "",
                email = firebaseUser.email ?: "",
                phone = firebaseUser.phoneNumber ?: ""
            )

            return Result.success(user)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }



    suspend fun signup(name: String, email: String, phone: String, password: String): Result<Users> {
        try {
            val result = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!

            val userId = firebaseUser.uid

            val user = Users(
                id = userId,
                role = "tracker",
                fullName = name,
                email = email,
                phone = phone
            )

            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            firebaseUser.updateProfile(profileUpdates).await()

            return Result.success(user)
        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

    suspend fun resetPassword(email: String): Result<Boolean> {
        try {
            val result = FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
//    fun getCurrentUser(): Users? {
//        val firebaseUser = FirebaseAuth.getInstance().currentUser
//        return if (firebaseUser != null) {
//            Users(
//                id = firebaseUser.uid,
//                role = "customer",
//                fullName = firebaseUser.displayName ?: "",
//                email = firebaseUser.email ?: "",
//                phone = firebaseUser.phoneNumber ?: ""
//            )
//        } else {
//            null
//        }

    fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
    }
