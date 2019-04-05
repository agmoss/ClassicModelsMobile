package com.example.classicmodelsmobile.presenter


import android.provider.Contacts
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.repository.Accessor
import com.example.classicmodelsmobile.repository.DbHelper
import com.github.kittinunf.fuel.core.Handler
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.*


class OrderPresenter(orderView:OrderMvp.OrderView, db: DbHelper, ac: Accessor) : OrderMvp.OrderPresenter {

    private val orderView: OrderMvp.OrderView = orderView
    private val db : DbHelper = db
    private val ac : Accessor = ac

    override fun insertData(order: Order) {
        if(db.insertData(order)){
            orderView.setResult("New data added")
            getAllData()
        }else{
            orderView.setResult("Failed add data")
        }
    }

    override fun getAllData() {

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders"

        var Orders = ArrayList<Order>()

        URL.httpGet().responseObject(Order.Deserializer()) { request, response, result ->
            val (order, err) = result

            println("API RESULT $result")
            //Add to ArrayList
            order?.forEach { ord ->

                Orders.add(ord)
            }

            orderView.setData(Orders)

        }

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