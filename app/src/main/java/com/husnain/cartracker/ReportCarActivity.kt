package com.husnain.cartracker

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.husnain.cartracker.databinding.ActivityReportCarBinding
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class ReportCarActivity : AppCompatActivity() {
    private var uri: Uri? = null
    private lateinit var binding: ActivityReportCarBinding
    private lateinit var viewModel: ReportCarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ReportCarViewModel()

        // Initialize Spinners
        setupPostTypeSpinner() // Spinner for "Lost" or "Found"

        // Observe ViewModel for save success
        lifecycleScope.launch {
            viewModel.isSaving.collect {
                it?.let {
                    if (it) {
                        Toast.makeText(this@ReportCarActivity, "Successfully saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }

        // Observe ViewModel for failure messages
        lifecycleScope.launch {
            viewModel.failureMessage.collect {
                it?.let {
                    Toast.makeText(this@ReportCarActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Handle form submission
        binding.btnReport.setOnClickListener {
            handleFormSubmission()
        }

//        binding.imageView2.setOnClickListener {
//            chooseImageFromGallery()
//        }
    }

    private fun setupPostTypeSpinner() {
        // Spinner values for "Lost" or "Found"
        val postTypes = listOf("Lost", "Found")

        // Set up the ArrayAdapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            postTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Attach the adapter to the Spinner
        binding.spinner.adapter = adapter
    }


    private fun handleFormSubmission() {
        val owner_name = binding.nameinput.text.toString().trim()
        val address = binding.addressinput.text.toString().trim()
        val owner_number = binding.contactinput.text.toString().trim()
        val date = binding.date.text.toString().trim()
        val carcolor = binding.color.text.toString().trim()
        val carmodel = binding.modelinput.text.toString().trim()
        val cartype = binding.spinner.selectedItem?.toString()?.trim() ?: ""
        val status = cartype
        val reg_number = binding.registrationinput.text.toString().trim()
        // val image = binding.upload.toString().trim()

        // Validate the input fields
        if (owner_name.isEmpty() || address.isEmpty() || owner_number.isEmpty()
            || date.isEmpty() || carcolor.isEmpty() || carmodel.isEmpty() || cartype.isEmpty() || reg_number.isEmpty()
        ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a Lost object
        val cardetails = CarDetails()
        cardetails.owner_name = owner_name
        cardetails.address = address
        cardetails.owner_name = owner_name
        cardetails.owner_number = owner_number
        cardetails.date = date
        cardetails.status = status
        cardetails.carcolor = carcolor
        cardetails.carmodel = carmodel
//        cardetails.isLost = cartype == "Lost" // Determine if it is lost based on Spinner selection
//        cardetails.isFound = cartype == "Found"
        //lost.image = image

//        val tvStatus: TextView = findViewById(R.id.tv_status)
//
//// Example status value (you'll fetch this dynamically from your data source)
//        val carStatus = "Lost" // or "Found"
//
//// Set the status text and background color based on the condition
//        when (carStatus) {
//            "Found" -> {
//                tvStatus.text = "Found"
//                tvStatus.setBackgroundResource(R.drawable.status_found)
//            }
//            "Lost" -> {
//                tvStatus.text = "Lost"
//                tvStatus.setBackgroundResource(R.drawable.status_lost)
//            }
//        }




        if (uri == null)
            viewModel.savedetail(cardetails)
//        else
//            viewModel.uploadImageAndSaveLost(getRealPathFromURI(uri!!)!!, lost)

        // Save the Handcraft object (this would be a database operation, Firestore, etc.)
        // For now, just display the success message
        Toast.makeText(this, "Post Save Successfully!", Toast.LENGTH_SHORT).show()



    }
}


