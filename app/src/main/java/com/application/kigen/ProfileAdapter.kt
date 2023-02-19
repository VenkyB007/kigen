import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu as AppCompatPopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.application.kigen.*
import kotlinx.android.synthetic.main.profile_view.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.Files.delete

class ProfileAdapter(var data: List<ProfileInfo>) : RecyclerView.Adapter<ProfileAdapter.profileViewHolder>() {

    class profileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name = itemView.profile_name
        var total = itemView.total
        var layout = itemView.mylayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): profileViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_view, parent, false)
        return profileViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("DiscouragedPrivateApi", "RtlHardcoded")
    override fun onBindViewHolder(holder: profileViewHolder, position: Int) {

        val database: myDatabase = Room.databaseBuilder(
            holder.itemView.context, myDatabase::class.java, "Kigen"
        ).build()
        GlobalScope.launch {
            database.dao().getProfileTotalExpense(position)
        }


        holder.name.text = data[position].name
        holder.total.text = DataObject.getAllTotalByProfileId(position)

        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,ExpenseActivity::class.java)
            intent.putExtra("position",position)
            holder.itemView.context.startActivity(intent)
        }


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
                            database.dao().deleteProfilebyName(holder.name.text as String)
                        }
                        DataObject.deleteProfilebyPos(position)
                        (holder.itemView.context as MainActivity).setRecycler()
                        Toast.makeText(view.context, "Deleted profile (${holder.name.text as String})", Toast.LENGTH_SHORT).show()
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
                //popupMenu.setPopupBackgroundResource(R.color.black)
            }catch (e: java.lang.Exception){
                Log.e("Main","Error showing menu icons. ",e)
            }finally {
                popupMenu.show()
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
