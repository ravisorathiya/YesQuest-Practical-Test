package com.app.lock.code.testproject.presentation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lock.code.testproject.data.model.Box
import com.app.lock.code.testproject.databinding.BoxItemBinding

class GridAdapter(private val grid: List<List<Box>>) :
    RecyclerView.Adapter<GridAdapter.BoxViewHolder>() {

    private var cliked = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        return BoxViewHolder(
            BoxItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {

        val row = position / grid[0].size
        val col = position % grid[0].size
        val box = grid[row][col]

        holder.itemView.setBackgroundColor(if (box.isClicked) Color.GREEN else Color.BLUE)

        holder.itemView.setOnClickListener {

            if (!cliked) {
                cliked = true
                box.isClicked = true
                updateSurroundingBoxes(row, col)
                notifyDataSetChanged() // Notify the adapter to refresh the UI
            }
        }
    }

//    private fun updateSurroundingBoxes(row: Int, col: Int) {
//        val directions = listOf(-1, 0, 1)
//        for (dr in directions) {
//            for (dc in directions) {
//                if (dr == 0 && dc == 0) continue
//
//                val newRow = row + dr
//                val newCol = col + dc
//
//                if (newRow in grid.indices && newCol in grid[0].indices) {
//                    if (!grid[newRow][newCol].isClicked) {
//                        grid[newRow][newCol].isClicked = true
//                    }
//                }
//            }
//        }
//    }

    private fun updateSurroundingBoxes(row: Int, col: Int) {
        // Define the offsets for the top, bottom, left, and right positions
        val offsets = listOf(Pair(-1,  0), Pair(1,  0), Pair(0, -1), Pair(0,  1))

        for ((dr, dc) in offsets) {
            val newRow = row + dr
            val newCol = col + dc

            // Check if the new position is within the grid boundaries
            if (newRow in grid.indices && newCol in grid[0].indices) {
                // Mark the box as clicked if it hasn't been clicked yet
                grid[newRow][newCol].isClicked = true
            }
        }
    }

    override fun getItemCount(): Int {
        return grid.sumBy { it.size }
    }

    class BoxViewHolder(itemView: BoxItemBinding) : RecyclerView.ViewHolder(itemView.root)
}
