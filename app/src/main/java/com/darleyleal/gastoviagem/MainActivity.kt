package com.darleyleal.gastoviagem

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.darleyleal.gastoviagem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonCalculate.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_calculate) {
            calculate()
        }
    }

    private fun calculate() {
        try {
            if (valuesIsNotEmpty() && valuesIsMajorOfZero()) {
                val distance = binding.editDistance.text.toString().toFloat()
                val price = binding.editPrice.text.toString().toFloat()
                val autonomy = binding.editAutonomy.text.toString().toFloat()

                val totalValue = "R$ ${"%.2f".format((price * distance) / autonomy)}"
                binding.textAmountToPay.text = totalValue
            }
        } catch (exception: IllegalArgumentException) {
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun valuesIsNotEmpty(): Boolean {
        if (binding.editDistance.text.toString().isNotEmpty() &&
            binding.editPrice.text.toString().isNotEmpty() &&
            binding.editAutonomy.text.toString().isNotEmpty()
        )
            return true
        else
            throw IllegalArgumentException("O campo é obrigatório!")
    }

    private fun valuesIsMajorOfZero(): Boolean {
        if (binding.editDistance.text.toString().toFloat() > 0 &&
            binding.editPrice.text.toString().toFloat() > 0 &&
            binding.editAutonomy.text.toString().toFloat() > 0
        )
            return true
        else
            throw IllegalArgumentException("Digite um valor maior que 0!")
    }
}