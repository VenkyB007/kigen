
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


    fun getProfileExpense(pos: Int): List<ExpenseInfo>{
        val profileExpense = mutableListOf<ExpenseInfo>()
        for (i in listExpense){
            if (i.profileId==pos){
                profileExpense.add(i)
            }
        }
        return profileExpense
    }

    fun deleteAllProfileExpenses(profileId: Int) :List<ExpenseInfo> {
        val iterator = listExpense.iterator()
        while (iterator.hasNext()) {
            val i = iterator.next()
            if (i.profileId == profileId) {
                iterator.remove()
            }
        }
        return listExpense
    }
    fun deleteData(pos:Int): ExpenseInfo{
        return listExpense[pos]
    }
    fun updateData(pos:Int,name:String,price:String){
        listExpense[pos].name=name
        listExpense[pos].price=price
    }
}
