package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.Order

class OrderMvp {

    interface OrderView{
        fun setData(listOrders: List<Order>)
        fun setEmpty()
        fun setResult(message: String)
        fun onLoad(isLoad: Boolean)

    }

    interface OrderPresenter{
        fun insertData(order: Order)
        fun deleteData(order: Order)
        fun updateData(order: Order)
        fun getAllData()
    }
}