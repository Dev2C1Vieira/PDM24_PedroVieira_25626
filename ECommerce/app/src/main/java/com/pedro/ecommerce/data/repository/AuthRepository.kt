package com.pedro.ecommerce.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Classe responsável por lidar diretamente com o Firebase Authentication.
 * Contém métodos para login, registo e logout de utilizadores.
 */
class AuthRepository {

    // Instância do Firebase Authentication
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Realiza o login de um utilizador com email e senha.
     * @param email Email do utilizador.
     * @param password Senha do utilizador.
     * @param onResult Callback para retornar o utilizador logado ou uma mensagem de erro.
     */
    fun login(email: String, password: String, onResult: (FirebaseUser?, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Retorna o utilizador logado em caso de sucesso
                    onResult(auth.currentUser, null)
                } else {
                    // Retorna a mensagem de erro caso algo falhe
                    onResult(null, task.exception?.message)
                }
            }
    }

    /**
     * Regista um novo utilizador com email e senha.
     * @param email Email do utilizador.
     * @param password Senha do utilizador.
     * @param onResult Callback para retornar o utilizador registado ou uma mensagem de erro.
     */
    fun register(email: String, password: String, onResult: (FirebaseUser?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Retorna o utilizador criado em caso de sucesso
                    onResult(auth.currentUser, null)
                } else {
                    // Retorna a mensagem de erro caso algo falhe
                    onResult(null, task.exception?.message)
                }
            }
    }

    /**
     * Faz logout do utilizador atualmente autenticado.
     */
    fun logout() {
        auth.signOut()
    }

    /**
     * Obtém o utilizador atualmente logado.
     * @return FirebaseUser ou null se não houver utilizador logado.
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}
