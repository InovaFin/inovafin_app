package com.example.inovafin.Util

import android.util.Patterns

class Validacao {
    companion object {
        fun validarEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches() // Verifica se o email é válido
        }

        fun validarNome(nome: String): Boolean {
            return nome.isNotEmpty() && nome.length >= 3 // Verifica se a senha é nula e se possui mais de 3 caracteres
        }

        fun validarSenha(senha: String?, confirmSenha: String?): Int? {
            if (senha != null && confirmSenha != null) {
                if (senha.length >= 6) {
                    if (senha == confirmSenha) {
                        return null // Senha válida
                    } else {
                        return 1 // Senhas não coincidem
                    }
                } else {
                    return 2 // Senha muito curta
                }
            } else {
                return 3 // Senha ou confirmação de senha nulas
            }
        }
    }
}
