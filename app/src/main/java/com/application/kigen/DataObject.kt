package com.application.kigen

object DataObject{
    var listProfiles = mutableListOf<ProfileInfo>()

    fun getAllData(): List<ProfileInfo> {
        return listProfiles
    }
    fun setProfileData(id:Int,name: String){
        listProfiles.add(ProfileInfo(name,0))
    }



    var listExpense = mutableListOf<ExpenseInfo>()
    var listoflistExpense: MutableList<MutableList<ExpenseInfo>> = mutableListOf()

    fun getEmptyData(): List<ExpenseInfo>{
        return listExpense
    }
    fun getalldata(): MutableList<MutableList<ExpenseInfo>>{
        return listoflistExpense
    }
//    fun setExpenseData(pos: Int, name: String, price: String){
//        listExpense.add(ExpenseInfo(name,price))
//        listoflistExpense.add(listExpense)
//    }
    fun setExpenseData(name:String,price: String){
        listExpense.add(ExpenseInfo(name,price))
    }
    fun getAllExpenseData(pos:Int): List<ExpenseInfo>{
        if (listoflistExpense[pos].isEmpty()){
            return listExpense
        }
        return listoflistExpense[pos]
    }
    fun getData(pos: Int): ExpenseInfo{
        return listExpense[pos]
    }
    fun deleteData(pos:Int): ExpenseInfo{
        return listExpense[pos]
    }
    fun updateData(pos:Int,name:String,price:String){
        listExpense[pos].name=name
        listExpense[pos].price=price
    }
}