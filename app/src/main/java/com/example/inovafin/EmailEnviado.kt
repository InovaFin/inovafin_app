package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityEmailEnviadoBinding

class EmailEnviado : AppCompatActivity() {

    private lateinit var binding: ActivityEmailEnviadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailEnviadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btLogin.setOnClickListener {
            val i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }
}