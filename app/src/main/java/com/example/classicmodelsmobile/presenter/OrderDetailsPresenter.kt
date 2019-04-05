package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import com.example.classicmodelsmobile.repository.DbHelper

class OrderDetailsPresenter(orderDetailsView: OrderDetailsMvp.OrderDetailsView, db:DbHelper):OrderDetailsMvp.OrderPresenter {

    private val orderDetailsView: OrderDetailsMvp.OrderDetailsView = orderDetailsView
    private val db: DbHelper = db

    override fun insertData(orderDetail: OrderDetails) {
        if(db.insertData(orderDetail)){
            orderDetailsView.setResult("New data added")
            getAllData()
        }else{
            orderDetailsView.setResult("Failed add data")
        }
    }

    override fun getAllData(){
        val orderList: List<OrderDetails> = db.getOrderDetails()
        orderDetailsView.onLoad(true)

        if(orderList.isNotEmpty())
            orderDetailsView.setData(orderList)
        else
            orderDetailsView.setEmpty()

        orderDetailsView.onLoad(false)
    }

    fun populateDetails(details : ArrayList<OrderDetails>){
        orderDetailsView.onLoad(true)

        if(details.isNotEmpty())
            orderDetailsView.setData(details)
        else
            orderDetailsView.setEmpty()

        orderDetailsView.onLoad(false)

    }

    override fun deleteData(id: Int) {
        if(db.deleteData(id)){
            orderDetailsView.setResult("Data deleted")
            getAllData()
        }else{
            orderDetailsView.setResult("Failed delete data")
        }
    }

    override fun updateData(orderDetail: OrderDetails) {
        if(db.updateData(orderDetail)){
            orderDetailsView.setResult("Data updated")
            getAllData()
        }else{
            orderDetailsView.setResult("Failed update data")
        }
    }
}