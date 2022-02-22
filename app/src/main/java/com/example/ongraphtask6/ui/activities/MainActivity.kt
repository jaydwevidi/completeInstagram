package com.example.ongraphtask6.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.ongraphtask6.databinding.ActivityMainBinding
import com.example.ongraphtask6.retrofit.functionality.LoginBuilderInstance
import com.example.ongraphtask6.retrofit.models.login.LoginBody
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG = "Jay MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.myBottomS).apply {
            peekHeight = 300
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.redirectToRegisterButton.setOnClickListener {
            startActivity(Intent(applicationContext , RegisterActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            logUserIn()
        }

        binding.textViewCheatLogin.setOnClickListener {
            toFeedActivity(211)
        }
    }

    private fun logUserIn(){
        val email = binding.emailloginet.text.toString()
        val password = binding.loginpasswordet.text.toString()

        if(email == "")
        {
            binding.loginPasswordETHolder.helperText = "Email Address Empty"
            return
        }

        if(password == "")
        {
            binding.loginPasswordETHolder.helperText = "Password Empty"
            return
        }

        lifecycleScope.launchWhenCreated {
            val response = try {
                LoginBuilderInstance.builderAPI.login(
                    LoginBody(
                        "sadfsdsdafds",
                        email,
                        password
                    )
                )
            }

            catch (e: Exception) {
                binding.loginPasswordETHolder.helperText = e.message
                Log.d(TAG, "onCreate: login Exception $e  , ${e.message}")
                return@launchWhenCreated


            }

            if(response.isSuccessful){
                val body = response.body()
                Log.d(TAG, "onCreate: response successful ${response.body()}")
                if (body!!.status){
                    Log.d(TAG, "logUserIn: ${body.data}")
                    val id = Integer.parseInt(body.data.id)
                    toFeedActivity(id)
                }
                else{
                    binding.loginPasswordETHolder.helperText = body.message
                }
            }
            else{
                Log.d(TAG, "onCreate: response unsuccessful")
            }
        }
    }

    private fun toFeedActivity(user_id : Int ){
        val intent = Intent(applicationContext , DashboardActivity::class.java)
        intent.putExtra("uid" , user_id)
        startActivity(intent)
    }
}