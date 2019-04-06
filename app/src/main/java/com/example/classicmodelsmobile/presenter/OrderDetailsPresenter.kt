package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.google.gson.Gson

class OrderDetailsPresenter(orderDetailsView: OrderDetailsMvp.OrderDetailsView) : OrderDetailsMvp.OrderPresenter {

    private val orderDetailsView: OrderDetailsMvp.OrderDetailsView = orderDetailsView


    override fun insertData(orderDetail: OrderDetails) {

        val orderJson = Gson().toJson(orderDetail)

        val URL = "https://classicmodelsrest.azurewebsites.net/api/orderdetail/"

        URL.httpPost().header("Content-Type" to "application/json").body(orderJson.toString())
            .response { req, res, result ->
                val (res, err) = result

                println(result)

                if (err == null) {
                    orderDetailsView.setResult("New data added")
                    //TODO: Refresh
                    //getAllData()
                } else {
                    orderDetailsView.setResult(err.toString() + res.toString())
                }
            }
    }

    override fun getAllData(order: Order) {


        val URL = "https://classicmodelsrest.azurewebsites.net/api/orders/" + order.orderNumber + "/orderdetails"

        var orderDetails = ArrayList<OrderDetails>()

        URL.httpGet().responseObject(OrderDetails.Deserializer()) { request, response, result ->
            val (details, err) = result

            println("API RESULT $result")
            //Add to ArrayList
            details?.forEach { deet ->

                orderDetails.add(deet)
            }
            orderDetailsView.setEmpty()
            orderDetailsView.setData(orderDetails)
        }

    }

    override fun deleteData(orderDetail: OrderDetails) {

        val URL =
            "https://classicmodelsrest.azurewebsites.net/api/orderdetail/" + orderDetail.orderNumber + "/" + orderDetail.productCode

        URL.httpDelete().header("Content-Type" to "application/json").response { req, res, result ->

            val (res, err) = result

            if (err == null) {
                orderDetailsView.setResult("Data Deleted")
                //TODO: Refresh
                //getAllData()
            } else {
                orderDetailsView.setResult(err.toString() + res.toString())
            }
        }
    }

    override fun updateData(orderDetail: OrderDetails) {

        val orderJson = Gson().toJson(orderDetail)

        val URL =
            "https://classicmodelsrest.azurewebsites.net/api/orderdetail/" + orderDetail.orderNumber + "/" + orderDetail.productCode

        URL.httpPut().header("Content-Type" to "application/json").body(orderJson.toString())
            .response { req, res, result ->
                val (res, err) = result

                println(result)

                if (err == null) {
                    orderDetailsView.setResult("Data updated")
                    //TODO: Refresh
                    //getAllData()
                } else {
                    orderDetailsView.setResult(err.toString() + res.toString())
                }
            }
    }
}