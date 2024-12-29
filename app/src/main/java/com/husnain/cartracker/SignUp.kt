package com.husnain.cartracker

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.husnain.cartracker.databinding.ActivitySignupBinding
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {
    lateinit var progressDialog:ProgressDialog
    lateinit var binding:ActivitySignupBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel=AuthViewModel()
//        viewModel.checkUser()
//           viewModel.getCurrentUser()
        progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Creating your account")
        progressDialog.setCancelable(false)

        lifecycleScope.launch {
            viewModel.failureMessage.collect{
                progressDialog.dismiss()
                if (it!=null){
                    Toast.makeText(this@SignUp,it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentUser.collect{
                if (it!=null){
                    progressDialog.dismiss()
                    startActivity(Intent(this@SignUp, MainActivity::class.java))
                    finish()
                }
            }
        }

        binding.logintext.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
        binding.singupbtn.setOnClickListener {
            val email=binding.email.editText?.text.toString()
            val password=binding.password.editText?.text.toString()
            val confirPassword=binding.confirmPassword.editText?.text.toString()
            val name=binding.name.editText?.text.toString()
            val phone=binding.phone.editText?.text.toString()

            if(name.toString().trim().isEmpty()){
                Toast.makeText(this,"Enter your name",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!email.contains("@")){
                Toast.makeText(this,"InValid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length<6){
                Toast.makeText(this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!password.equals(confirPassword)){
                Toast.makeText(this,"Confirm password does not match with password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(phone.length<6){
                Toast.makeText(this,"Phone must be at least 11 Digits",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressDialog.show()

            viewModel.signUp(name, email,phone,password)

        }



    }
}