package com.powersoft.miustore

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.powersoft.miustore.databinding.ActivityRegisterBinding
import com.powersoft.miustore.models.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateAccount.setOnClickListener {
            if (!validate()) return@setOnClickListener
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(firstName, lastName, email, password)

            Toast.makeText(this, "User Created Successfully!!", Toast.LENGTH_SHORT).show()
            val intent = Intent()
            intent.putExtra("user", user)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validate(): Boolean {
        if (binding.etFirstName.text.isEmpty()) {
            binding.etFirstName.error = "First Name is required!!"
            binding.etFirstName.requestFocus()
            return false
        } else if (binding.etLastName.text.isEmpty()) {
            binding.etLastName.error = "Last Name is required!!"
            binding.etLastName.requestFocus()
            return false
        } else if (binding.etEmail.text.isEmpty()) {
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