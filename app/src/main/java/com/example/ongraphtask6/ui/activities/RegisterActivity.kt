package com.example.ongraphtask6.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ongraphtask6.R
import com.example.ongraphtask6.databinding.ActivityRegisterBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.myBottomS).apply {
            peekHeight = 300
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}