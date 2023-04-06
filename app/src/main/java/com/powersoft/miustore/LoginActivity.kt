package com.powersoft.miustore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.powersoft.miustore.databinding.ActivityLoginBinding
import com.powersoft.miustore.models.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var userList = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userList.add(User("Roman", "Khadka", "roman.khadka@miu.edu", "12345"))
        userList.add(User("Sailesh", "limbu", "sailesh.limbu@miu.edu", "12345"))
        userList.add(User("Dipesh", "KC", "dipesh.kc@miu.edu", "12345"))
        userList.add(User("Kushal", "Shrestha", "kushal.shrestha@miu.edu", "12345"))

        val contract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val user = it.data?.getSerializableExtra("user") as User
                    userList.add(user)
                } else if (it.resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled by User", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnCreateAccount.setOnClickListener {
            contract.launch(Intent(this, RegisterActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            if (!validate()) return@setOnClickListener
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val user = User("", "", email, password)
            if (checkIfUserExist(user)) {
                val intent = Intent(this, ShoppingActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            } else {
                Toast.makeText(this, "User not found!!!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvForgotPassword.setOnClickListener {
            if (binding.etEmail.text.isEmpty()) {
                binding.etEmail.error = "Please enter your email!!"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            val email = binding.etEmail.text.toString()

            val intent = Intent()
            intent.action = Intent.ACTION_SENDTO
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Forgot Password")
            intent.putExtra(Intent.EXTRA_TEXT, "Your Password is ${getPasswordFromEmail(email)}")

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No application found for email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPasswordFromEmail(email: String): String? {
        userList.forEach { if (it.email == email) return it.password }
        return null
    }

    private fun checkIfUserExist(user: User): Boolean {
        userList.forEach {
            if (it.email == user.email && it.password == user.password) return true
        }
        return false
    }

    private fun validate(): Boolean {
        if (binding.etEmail.text.isEmpty()) {
            binding.etEmail.error = "Email is required!!"
            binding.etEmail.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()) {
            binding.etEmail.error = "Invalid email address!!"
            binding.etEmail.requestFocus()
            return false
        } else if (binding.etPassword.text.isEmpty()) {
            binding.etPassword.error = "Password is required!!"
            binding.etPassword.requestFocus()
            return false
        }
        return true
    }
}