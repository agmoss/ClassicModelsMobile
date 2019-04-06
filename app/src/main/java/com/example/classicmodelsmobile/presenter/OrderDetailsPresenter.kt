package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.OrderDetails
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson

class OrderDetailsPresenter(orderDetailsView: OrderDetailsMvp.OrderDetailsView):OrderDetailsMvp.OrderPresenter {
    override fun getAllData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val orderDetailsView: OrderDetailsMvp.OrderDetailsView = orderDetailsView


    override fun insertData(orderDetail: OrderDetails) {

        val orderJson = Gson().toJson(orderDetail)

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orderdetail/"

        URL.httpPost().header("Content-Type" to "application/json").body(orderJson.toString()).response { req, res, result ->
            val (res, err) = result

            println(result)

            if(err == null){
                orderDetailsView.setResult("New data added")
                //TODO: Refresh
                //getAllData()
            }else{
                orderDetailsView.setResult(err.toString() + res.toString())
            }
        }
    }

/*    override fun getAllData(){
        val orderList: List<OrderDetails> = db.getOrderDetails()
        orderDetailsView.onLoad(true)

        if(orderList.isNotEmpty())
            orderDetailsView.setData(orderList)
        else
            orderDetailsView.setEmpty()

        orderDetailsView.onLoad(false)
    }*/

    fun populateDetails(details : ArrayList<OrderDetails>){
        orderDetailsView.onLoad(true)

        if(details.isNotEmpty())
            orderDetailsView.setData(details)
        else
            orderDetailsView.setEmpty()

        orderDetailsView.onLoad(false)

    }

     override fun deleteData(orderDetail: OrderDetails) {

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orderdetail/"+orderDetail.orderNumber + "/" + orderDetail.productCode

        URL.httpDelete().header("Content-Type" to "application/json").response{req, res, result ->

            val (res, err) = result

            if(err == null){
                orderDetailsView.setResult("Data Deleted")
                //TODO: Refresh
                //getAllData()
            }else{
                orderDetailsView.setResult(err.toString() + res.toString())
           }
        }
    }

    override fun updateData(orderDetail: OrderDetails) {

        val orderJson = Gson().toJson(orderDetail)

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orderdetail/"+orderDetail.orderNumber + "/" + orderDetail.productCode

        URL.httpPut().header("Content-Type" to "application/json").body(orderJson.toString()).response { req, res, result ->
            val (res, err) = result

            println(result)

            if(err == null){
                orderDetailsView.setResult("Data updated")
                //TODO: Refresh
                //getAllData()
            }else{
                orderDetailsView.setResult(err.toString() + res.toString())
            }
        }
    }
}