package com.example.classicmodelsmobile.presenter

import com.example.classicmodelsmobile.model.OrderDetails

class OrderDetailsMvp {

    interface OrderDetailsView{
        fun setData(listOrders: List<OrderDetails>)
        fun setEmpty()
        fun setResult(message: String)
        fun onLoad(isLoad: Boolean)

    }

    interface OrderPresenter{
        fun insertData(orderDetail: OrderDetails)
        fun deleteData(orderDetail: OrderDetails)
        fun updateData(orderDetail: OrderDetails)
        fun getAllData()
    }
}