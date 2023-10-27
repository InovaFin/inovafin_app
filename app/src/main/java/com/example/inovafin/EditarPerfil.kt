package com.example.inovafin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityEditarPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditarPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAlterarFoto.setOnClickListener {
            pickImage()
        }

        binding.btAlterarDados.setOnClickListener {
            validarCampos()
        }
    }

    fun validarCampos() {
        val nome = binding.nomeUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()

        if (nome.isNotEmpty() && email.isNotEmpty()) {
            alterarDados()
        } else {
            if (nome.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha seu Nome", Toast.LENGTH_LONG).show()
            } else if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha seu Email", Toast.LENGTH_LONG).show()
            } else if (nome.isEmpty() && email.isEmpty()) {
                Toast.makeText(applicationContext, "Se quiser manter os dados preencha-os EXATAMENTE como são", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun alterarDados() {
        // Altera nome e foto
        val nome = binding.nomeUsuario.text.toString()

        val usuarioId = autentificacao.currentUser!!.uid

        val usuarioMasp = hashMapOf(
            "foto" to selectedImageUri,
            "nome" to nome
        )

        firestore.collection("Usuarios").document(usuarioId)
            .update(usuarioMasp as Map<String, Any>).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Dados Alterados!", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar os dados! " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
                }
            }
        // Altera nome e foto

        // Altera email
        val email = binding.emailUsuario.text.toString()
        val usuarioAuth = autentificacao.currentUser

        usuarioAuth!!.updateEmail("$email")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Email alterado", Toast.LENGTH_LONG).show()
                }
                else {
                    val excecao = "Erro ao alterar o email: ${task.exception?.message}"
                    Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                    binding.msgErro.text = excecao
                }
            }
        // Altera email
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
                    selectedImageUri = data.data
                    binding.imagemUsuario.setImageURI(selectedImageUri)
                } else {
                    Toast.makeText(applicationContext, "Nenhuma imagem selecionada", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Seleção de imagem cancelada", Toast.LENGTH_LONG).show()
            }
        }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {

            val usuarioId = autentificacao.currentUser!!.uid

            val emailUsuario = autentificacao.currentUser!!.email

            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        // Aplica os dados no layout
                        binding.nomeUsuario.hint = document.getString("nome")
                        binding.emailUsuario.hint = emailUsuario
                    }
                }
        }
    }

}