package com.pedro.ecommerce.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Classe responsável por lidar diretamente com o Firebase Authentication e Firestore.
 * Contém métodos para login, registo e logout de utilizadores.
 */
class AuthRepository {

    // Instância do Firebase Authentication e Firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
     * @param userName Nome do utilizador.
     * @param onResult Callback para retornar o utilizador registado ou uma mensagem de erro.
     */
    fun registerUser(email: String, password: String, userName: String, onResult: (FirebaseUser?, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Adiciona o utilizador à coleção 'users'
                        val userData = mapOf(
                            "id" to it.uid,
                            "name" to userName,
                            "email" to email
                        )

                        firestore.collection("users").document(it.uid)
                            .set(userData)
                            .addOnSuccessListener {
                                // Cria um documento inicial na coleção 'carts' para o utilizador
                                val cartData = mapOf(
                                    "userId" to user.uid,
                                    "items" to emptyList<Map<String, Any>>(),
                                    "totalPrice" to 0.0
                                )

                                firestore.collection("carts").document(user.uid)
                                    .set(cartData)
                                    .addOnSuccessListener {
                                        // Sucesso ao criar utilizador e carrinho
                                        onResult(user, null)
                                    }
                                    .addOnFailureListener { error ->
                                        // Falha ao criar o carrinho
                                        onResult(null, "Erro ao criar carrinho: ${error.message}")
                                    }
                            }
                            .addOnFailureListener { error ->
                                // Falha ao criar o utilizador na coleção 'users'
                                onResult(null, "Erro ao criar utilizador na base de dados: ${error.message}")
                            }
                    } ?: run {
                        onResult(null, "Erro ao obter utilizador após registo.")
                    }
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