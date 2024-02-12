package com.app.lock.code.testproject.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.lock.code.testproject.databinding.ActivityResultBinding
import com.app.lock.code.testproject.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("row", binding.edtRow.text.toString().toInt())
            bundle.putInt("col", binding.edtCol.text.toString().toInt())
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }
}