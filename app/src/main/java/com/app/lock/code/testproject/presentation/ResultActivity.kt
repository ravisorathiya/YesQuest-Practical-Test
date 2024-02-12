package com.app.lock.code.testproject.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.lock.code.testproject.R
import com.app.lock.code.testproject.data.model.Box
import com.app.lock.code.testproject.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var grid: List<List<Box>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val row = intent.extras?.getInt("row") ?: 3
        val col = intent.extras?.getInt("col") ?: 3
        grid = generateGrid(row, col)

        binding.recyclerView.layoutManager = GridLayoutManager(this, col)
        binding.recyclerView.adapter = GridAdapter(grid)


    }

    fun generateGrid(rows: Int, cols: Int): List<List<Box>> {
        return List(rows) { row ->
            List(cols) { col ->
                Box(row, col)
            }
        }
    }


}

