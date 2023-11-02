package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityTransferirValorBinding

class TransferirValor : AppCompatActivity() {

    private lateinit var binding: ActivityTransferirValorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferirValorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}