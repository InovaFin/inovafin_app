package com.example.inovafin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Video.Media
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityEditarPerfilBinding

class EditarPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAlterarFoto.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        resultLauncher.launch(i)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImageUri = data.data
                    binding.imagemUsuario.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(applicationContext, "Nenhuma imagem selecionada", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Seleção de imagem cancelada", Toast.LENGTH_LONG).show()
            }
        }

    private fun doSomeOperations() {
        // Coloque aqui qualquer operação adicional que você deseja realizar após selecionar a imagem.
    }
}