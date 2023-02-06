package com.application.kigen

import androidx.room.Room

object DataObject{
    var listProfiles = mutableListOf<ProfileInfo>()

    fun getAllData(): List<ProfileInfo> {
        return listProfiles
    }
    fun setProfileData(id:Int,name: String){
        listProfiles.add(ProfileInfo(name,0))
    }

    fun deleteAllProfiles(){
        listProfiles.clear()
    }

    ////////////////////////////////////////////
    /*          Expense Data Operations      */
    ///////////////////////////////////////////

    var listExpense = mutableListOf<ExpenseInfo>()

    fun setExpenseData(profileId: Int,name:String,price: String){
        listExpense.add(ExpenseInfo(profileId,name,price))
    }
    fun getData(pos: Int): ExpenseInfo{
        return listExpense[pos]
    }

    fun deleteAllProfileExpenses(profileId: Int) {
        for (i in listExpense){
            if (i.profileId == profileId){
                listExpense.remove(i)
            }
        }
    }
    fun deleteData(pos:Int): ExpenseInfo{
        return listExpense[pos]
    }
    fun updateData(pos:Int,name:String,price:String){
        listExpense[pos].name=name
        listExpense[pos].price=price
    }
}