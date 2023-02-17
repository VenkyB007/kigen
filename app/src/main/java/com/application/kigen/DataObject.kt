
package com.application.kigen

import androidx.room.Room

object DataObject{
    var listProfiles = mutableListOf<ProfileInfo>()

    fun isProfileNameUnique(name: String):Boolean{
        for(i in listProfiles){
            if (i.name == name){
                return false
            }
        }
        return true
    }

    fun getAllData(): List<ProfileInfo> {
        return listProfiles
    }
    fun setProfileData(id:Int,name: String){
        listProfiles.add(ProfileInfo(name,""))
    }

    fun deleteAllProfiles(){
        listProfiles.clear()
    }

    fun deleteProfilebyPos(pos: Int) {
        listProfiles.removeAt(pos)

    }

    fun getAllTotalByProfileId(pos: Int):String{
        var sum:Double = 0.0
        for (i in listExpense){
            if (i.profileId == pos){
                sum += (i.price).toDouble()
            }
        }
        return sum.toString()
    }

    ////////////////////////////////////////////
    /*          Expense Data Operations      */
    ///////////////////////////////////////////

    var listExpense = mutableListOf<ExpenseInfo>()

    fun setExpenseData(profileId: Int,name:String,price: String){
        listExpense.add(ExpenseInfo(profileId,name,price))
    }
    fun getData(pos: Int,epos: Int): ExpenseInfo{
        var profileExpense = mutableListOf<ExpenseInfo>()

        for(i in listExpense){
            if (i.profileId == pos){
                profileExpense.add(i)
            }
        }

        return profileExpense[epos]
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

    fun deleteExpensebyNameandPrice(name: String,price: String) {
        val iterator = listExpense.iterator()

        while (iterator.hasNext()){
            val item = iterator.next()
            if (item.name == name && item.price == price){
                iterator.remove()
            }
        }
    }
    fun updateData(name:String,price:String){
        val iterator = listExpense.iterator()

        while (iterator.hasNext()){
            val item = iterator.next()
            if (item.name == name && item.price == price){
                item.name = name
                item.price = price
            }
        }
    }
}
