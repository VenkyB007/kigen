package com.application.kigen

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.expense_view.view.*
import kotlinx.android.synthetic.main.profile_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileAdapter(var data: List<ProfileInfo>) : RecyclerView.Adapter<ProfileAdapter.profileViewHolder>() {
    class profileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.profile_name
        var total = itemView.total
        var layout = itemView.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): profileViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_view, parent, false)
        return profileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: profileViewHolder, position: Int) {

        holder.name.text = data[position].name
        holder.total.text = DataObject.getAllTotalByProfileId(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,ExpenseActivity::class.java)
            intent.putExtra("position",position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}