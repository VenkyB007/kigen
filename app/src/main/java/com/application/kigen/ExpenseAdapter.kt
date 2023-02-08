package com.application.kigen

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.expense_view.view.*

class ExpenseAdapter(var data: List<ExpenseInfo>) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var name = itemView.expense_name
        var price = itemView.price
        var layout = itemView.expenseLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.expense_view, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val item = data[position]
        holder.name.text = data[position].name
        holder.price.text = data[position].price
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