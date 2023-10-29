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
import com.google.firebase.auth.EmailAuthProvider
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
    private var senhaAlterada = false
    private var novaSenhaAlterada = false
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
        configurarTextWatcherSenha()
        configurarTextWatcherNovaSenha()

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
    }

    // Verifica o que está acontecendo com o EditText Nome
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

    // Verifica o que está acontecendo com o EditText Email
    private fun configurarTextWatcherEmail() {
        binding.emailUsuario.addTextChangedListener(object : TextWatcher {
            private var emailAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                emailAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (emailAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Email foi preenchido pela primeira vez ou o texto foi reescrito
                    emailAlterado = true
                } else if (!emailAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Email foi apagado
                    emailAlterado = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    // Verifica o que está acontecendo com o EditText Senha
    private fun configurarTextWatcherSenha() {
        binding.senhaUsuario.addTextChangedListener(object : TextWatcher {
            private var senhaAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                senhaAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (senhaAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Senha foi preenchido pela primeira vez ou o texto foi reescrito
                    senhaAlterada = true
                } else if (!senhaAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Senha foi apagado
                    senhaAlterada = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    // Verifica o que está acontecendo com o EditText Senha
    private fun configurarTextWatcherNovaSenha() {
        binding.novaSenha.addTextChangedListener(object : TextWatcher {
            private var senhaAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                senhaAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (senhaAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Senha foi preenchido pela primeira vez ou o texto foi reescrito
                    novaSenhaAlterada = true
                } else if (!senhaAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Senha foi apagado
                    novaSenhaAlterada = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    fun validarCampos() {
        if (nomeAlterado || emailAlterado || fotoAlterada || senhaAlterada || novaSenhaAlterada) {
            if (nomeAlterado) {
                alterarNome()
            }
            if (emailAlterado) {
                alterarEmail()
            }
            if (fotoAlterada) {
                alterarFoto()
            }
            if (senhaAlterada) {
                alterarSenha()
            }
            if (novaSenhaAlterada) {
                Toast.makeText(applicationContext, "Por favor, digite a senha atual", Toast.LENGTH_LONG).show()
            }
            recarregarApp()
        } else {
            Toast.makeText(applicationContext, "Nenhum campo foi alterado", Toast.LENGTH_LONG).show()
        }
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
                    Toast.makeText(applicationContext, "Nome Alterado", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar o nome " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                }
            }
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
                    Toast.makeText(applicationContext, "Email Firestore alterado", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar email Firestore " + e.message
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

    private fun alterarSenha() {
        val usuarioAuth = autentificacao.currentUser
        val senhaAtual = binding.senhaUsuario.text.toString()
        val novaSenha = binding.novaSenha.text.toString()

        val credencial = EmailAuthProvider.getCredential(usuarioAuth!!.email!!, senhaAtual)

        usuarioAuth.reauthenticate(credencial)
            .addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    // A reautenticação foi bem-sucedida, agora podemos alterar a senha
                    if (validarNovaSenha()) {
                        usuarioAuth.updatePassword(novaSenha)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(applicationContext, "Senha alterada com sucesso", Toast.LENGTH_LONG).show()
                                } else {
                                    val excecao = "Erro ao alterar a senha: ${updateTask.exception?.message}"
                                    Toast.makeText(applicationContext, excecao, Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(applicationContext, "Senha atual incorreta", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun validarNovaSenha(): Boolean {
        val novaSenha = binding.novaSenha.text.toString()

        if (novaSenha.isEmpty()) {
            Toast.makeText(applicationContext, "Por favor, digite a nova senha", Toast.LENGTH_LONG).show()
            return false
        } else if (novaSenha.length < 6) {
            Toast.makeText(applicationContext, "A nova senha deve ter pelo menos 6 caracteres", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun alterarFoto() {
        val usuarioId = autentificacao.currentUser!!.uid

        val usuarioMasp = hashMapOf(
            "foto" to selectedImageUri
        )

        firestore.collection("Usuarios").document(usuarioId)
            .update(usuarioMasp as Map<String, Any>).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Foto Alterada", Toast.LENGTH_LONG).show()
                } else {
                    var excecao = ""

                    try {
                        throw task.exception!!
                    } catch (e: Exception) {
                        excecao = "Erro ao alterar Foto: " + e.message
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun recarregarApp() {
        val i = Intent(this, SplashScreen::class.java)
        startActivity(i)
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

    private fun limparFoto() {
        val placeholderImage = R.drawable.ic_perfil // Substitua 'seu_icone_padrao' pelo nome do seu recurso de imagem
        binding.imagemUsuario.setImageResource(placeholderImage)
        selectedImageUri = null
        fotoAlterada = true
    }

    private fun resgatarDados() {
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

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {
            resgatarDados()
        }
    }
}