package com.application.kigen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.expense_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExpenseAdapter(var data: List<ExpenseInfo>, private val profilePosition: Int) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
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

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("DiscouragedPrivateApi", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {

        holder.name.text = data[position].name
        holder.price.text = data[position].price


        holder.itemView.setOnLongClickListener{view->

            val database: myDatabase = Room.databaseBuilder(
                view.context, myDatabase::class.java, "Kigen"
            ).build()

            val popupStyleAttr = androidx.appcompat.R.attr.popupMenuStyle
            val popupStyleRes = R.style.MyPopupMenu
            val popupMenu = androidx.appcompat.widget.PopupMenu(holder.itemView.context, holder.itemView, Gravity.RIGHT, popupStyleAttr, popupStyleRes)

            popupMenu.menuInflater.inflate(R.menu.profile_menu_lp, popupMenu.menu)
            popupMenu.menu.setGroupDividerEnabled(true)

            popupMenu.setOnMenuItemClickListener { menuItem->
                when(menuItem.itemId){
                    R.id.action_delete->{
                        GlobalScope.launch {
                            database.edao().deleteExpensebyNameandPrice(holder.name.text as String, holder.price.text as String)
                        }
                        DataObject.deleteExpensebyNameandPrice(holder.name.text as String,holder.price.text as String)
                        (holder.itemView.context as ExpenseActivity).setRecycler(profilePosition)
                        Toast.makeText(view.context, "Deleted Expense (${holder.name.text as String})", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.action_edit->{
                        val intent = Intent(holder.itemView.context,UpdateExpense::class.java)
                        intent.putExtra("profilePosition",profilePosition)
                        intent.putExtra("expensePosition",position)
                        holder.itemView.context.startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                    .invoke(mPopup,true)
            }catch (e: java.lang.Exception){
                Log.e("Main","Error showing menu icons. ",e)
            }finally {
                popupMenu.show()
            }
            true
        }

    }

    fun updateData(newData: List<ExpenseInfo>) {
        data = newData
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return data.size
    }
}