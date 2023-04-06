package com.powersoft.miustore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.powersoft.miustore.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityShoppingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvEmail.text = intent.getStringExtra("email")

        binding.cvBooks.setOnClickListener(this)
        binding.cvClothing.setOnClickListener(this)
        binding.cvOrganicFood.setOnClickListener(this)
        binding.cvMedicine.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.cvBooks -> showToast("Books is clicked!!")
            R.id.cvClothing -> showToast("Clothing is clicked!!")
            R.id.cvOrganicFood -> showToast("Organic Food is clicked!!")
            R.id.cvMedicine -> showToast("Medicine is clicked!!")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}