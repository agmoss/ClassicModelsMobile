package com.example.classicmodelsmobile.model

class OrderDetails(val orderNumber:Int,
                   val productCode:String,
                   val quantityOrdered: Int,
                   val priceEach: Double,
                   val orderLineNumber: Short) {
}