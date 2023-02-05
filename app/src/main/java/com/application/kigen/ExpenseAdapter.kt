package com.application.kigen

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expense_view.view.*
import kotlinx.android.synthetic.main.profile_view.view.*

class ExpenseAdapter(var data: List<ExpenseInfo>) : RecyclerView.Adapter<ExpenseAdapter.expenseViewHolder>() {
    class expenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.expense_name
        var price = itemView.price
        var layout = itemView.expenseLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): expenseViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.expense_view, parent, false)
        return expenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: expenseViewHolder, position: Int) {

        holder.name.text = data[position].name
        holder.price.text = data[position].price.toString()
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,UpdateExpense::class.java)
            intent.putExtra("id",position)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}