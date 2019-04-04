package com.example.classicmodelsmobile.presenter


import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.repository.DbHelper

class OrderPresenter(orderView:OrderMvp.OrderView, db: DbHelper) : OrderMvp.OrderPresenter {


    val orderView: OrderMvp.OrderView = orderView
    val db : DbHelper = db

    override fun insertData(order: Order) {
        if(db.insertData(order)){
            orderView.setResult("New data added")
            getAllData()
        }else{
            orderView.setResult("Failed add data")
        }
    }

    override fun getAllData(){
        val orderList: List<Order> = db.getAllOrders()
        orderView.onLoad(true)

        if(orderList.size > 0)
            orderView.setData(orderList)
        else
            orderView.setEmpty()

        orderView.onLoad(false)
    }

    override fun deleteData(id: Int) {
        if(db.deleteData(id)){
            orderView.setResult("Data deleted")
            getAllData()
        }else{
            orderView.setResult("Failed delete data")
        }
    }

    override fun updateData(order: Order) {
        if(db.updateData(order)){
            orderView.setResult("Data updated")
            getAllData()
        }else{
            orderView.setResult("Failed update data")
        }
    }
}