package com.husnain.cartracker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.husnain.cartracker.databinding.ActivityCarDetailBinding
import kotlinx.coroutines.launch

class CarDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarDetailBinding
    private lateinit var cars: CarDetails
    private lateinit var viewModel: CarDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = CarDetailViewModel()

        cars = Gson().fromJson(intent.getStringExtra("data"), CarDetails::class.java)

        binding.nameinput.text = intent.getStringExtra("nameinput")
        binding.contactinput.text = intent.getStringExtra("contactinput")
//        binding.date.text = intent.getStringExtra("date")
        binding.carmodel.text = intent.getStringExtra("carmodel")
        binding.carcolor.text = intent.getStringExtra("carcolor")
        binding.reg.text = intent.getStringExtra("reg")
        binding.addressinput.text = intent.getStringExtra("addressinput")




        // Set visibility for buttons
        val user: FirebaseUser = AuthRepository().getCurrentUser()!!
        val isAdmin = user.email == "sp22-bcs-138@students.cuisahiwal.edu.pk"

        if (!isAdmin || cars.status == "report detected") {
            binding.deleteCase.visibility = View.GONE
        }


        // Set button click listeners
        binding.carfound.setOnClickListener {
//             cars.isFound=true
            cars.status = "Car Found"
            viewModel.updatedetail(cars)
        }


        binding.deleteCase.setOnClickListener {
            // Call deleteLost function in ViewModel
            viewModel.deleteCase(cars.id)
        }

        // Observe updates to Lost object
        lifecycleScope.launch {
            viewModel.isUpdated.collect { isUpdated ->
                isUpdated?.let {
//
                    Toast.makeText(this@CarDetailActivity, "Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        // Observe deletion of Lost object
        lifecycleScope.launch {
            viewModel.isDeleted.collect { isDeleted ->
                isDeleted?.let {
                    if (it) {
                        Toast.makeText(this@CarDetailActivity, "Case deleted successfully", Toast.LENGTH_SHORT).show()
                        finish() // Close activity after deletion
                    }
                }
            }
        }

        // Observe failure messages
        lifecycleScope.launch {
            viewModel.failureMessage.collect { message ->
                message?.let {
                    Toast.makeText(this@CarDetailActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}