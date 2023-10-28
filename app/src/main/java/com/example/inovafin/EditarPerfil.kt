package com.example.inovafin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.textclassifier.TextLanguage
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityEditarPerfilBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class EditarPerfil : AppCompatActivity() {

    private lateinit var binding: ActivityEditarPerfilBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var selectedImageUri: Uri? = null

    private var nomeAlterado = false
    private var emailAlterado = false
    private var fotoAlterada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        configurarTextWatcherNome()
        configurarTextWatcherEmail()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAlterarFoto.setOnClickListener {
            pickImage()
        }

        binding.btAlterarDados.setOnClickListener {
            validarCampos()
        }

        binding.btExcluir.setOnClickListener {
            limparFoto()
        }

        binding.btAlterarSenha.setOnClickListener {
            dialogConfirmacao()
        }
    }
    fun dialogConfirmacao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Você quer receber um email para alterar sua senha?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Usuário confirmou a saída
            enviarSenha()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun enviarSenha() {
        val user = autentificacao.currentUser
        val newPassword = "1234567"

        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Senha alterada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Erro: ", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun configurarTextWatcherNome() {
        binding.nomeUsuario.addTextChangedListener(object : TextWatcher {
            private var nomeAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                nomeAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (nomeAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Nome foi preenchido pela primeira vez ou o texto foi reescrito
                    nomeAlterado = true
                } else if (!nomeAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Nome foi apagado
                    nomeAlterado = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    private fun configurarTextWatcherEmail() {
        binding.emailUsuario.addTextChangedListener(object : TextWatcher {
            private var nomeAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                nomeAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (nomeAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Nome foi preenchido pela primeira vez ou o texto foi reescrito
                    emailAlterado = true
                } else if (!nomeAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Nome foi apagado
                    emailAlterado = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    private fun limparFoto() {
        val placeholderImage = R.drawable.ic_perfil // Substitua 'seu_icone_padrao' pelo nome do seu recurso de imagem
        binding.imagemUsuario.setImageResource(placeholderImage)
        selectedImageUri = null
        fotoAlterada = true
    }

    fun validarCampos() {
        if (nomeAlterado || emailAlterado || fotoAlterada) {
            if (nomeAlterado) {
                alterarNome()
            }
            if (emailAlterado) {
                alterarEmail()
            }
            if (fotoAlterada) {
                alterarFoto()
            }
            recarregarApp()
        } else {
            Toast.makeText(applicationContext, "Nenhum campo foi alterado", Toast.LENGTH_LONG).show()
        }
    }

    private fun recarregarApp() {
        val i = Intent(this, SplashScreen::class.java)
        startActivity(i)
    }

    private fun alterarEmail() {
        val email = binding.emailUsuario.text.toString()
        val usuarioAuth = autentificacao.currentUser

        if (emailValido(email)) {
            usuarioAuth!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        alterarEmailFirestore()
                        Toast.makeText(applicationContext, "Email alterado", Toast.LENGTH_LONG).show()
                    } else {
                        val excecao = "Erro ao alterar o email: ${task.exception?.message}"
                        Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(applicationContext, "Email inválido", Toast.LENGTH_LONG).show()
        }
    }

    private fun alterarEmailFirestore() {
        val email = binding.emailUsuario.text.toString()
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .update("email", email).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Email Firestore alterado!", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar email Firestore! " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun emailValido(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }


    private fun alterarNome() {
        val novoNome = binding.nomeUsuario.text.toString()
        val usuarioId = autentificacao.currentUser!!.uid

        val usuarioMap = hashMapOf(
            "nome" to novoNome
        )

        firestore.collection("Usuarios").document(usuarioId)
            .update(usuarioMap as Map<String, Any>).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Nome Alterado!", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar o nome! " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun alterarFoto() {
        val usuarioId = autentificacao.currentUser!!.uid

        val usuarioMasp = hashMapOf(
            "foto" to selectedImageUri
        )

        firestore.collection("Usuarios").document(usuarioId)
            .update(usuarioMasp as Map<String, Any>).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Foto Alterada!", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar Foto! " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
                }
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
                    selectedImageUri = data.data
                    binding.imagemUsuario.setImageURI(selectedImageUri)
                    fotoAlterada = true
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

                        if (selectedImageUri != null) {
                            // Se uma imagem da galeria foi selecionada, exiba-a
                            Glide.with(this).load(selectedImageUri).into(binding.imagemUsuario)
                        } else {
                            val foto = document.getString("foto")
                            // Verifique se a foto do banco não é nula antes de carregar
                            if (!foto.isNullOrEmpty()) {
                                Glide.with(this).load(foto).into(binding.imagemUsuario)
                            }
                        }
                    }
                }
        }
    }
}