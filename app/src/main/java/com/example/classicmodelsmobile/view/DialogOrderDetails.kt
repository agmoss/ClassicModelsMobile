package com.example.classicmodelsmobile.view

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import com.example.classicmodelsmobile.presenter.OrderDetailsPresenter
import com.example.classicmodelsmobile.presenter.OrderPresenter

class DialogOrderDetails : Dialog {
    val ctx: Context = context
    val btnSubmit: Button
    val etProductCode: EditText?
    val etQuantityOrdered: EditText?
    val etPriceEach: EditText?
    val etOrderLineNumber: EditText?

    var mIsEdit:Boolean = false
    var ordDtl: OrderDetails? = null

    constructor(context: Context, presenter: OrderDetailsPresenter) : super(context) {

        setContentView(R.layout.create_detail_dialog)

        btnSubmit = findViewById(R.id.btnSubmit)
        etProductCode = findViewById(R.id.etProductCode)
        etQuantityOrdered = findViewById(R.id.etQuantityOrdered)
        etPriceEach = findViewById(R.id.etPriceEach)
        etOrderLineNumber = findViewById(R.id.etOrderLineNumber)

        btnSubmit.setOnClickListener {
            if(etProductCode.text.length > 0 && etPriceEach.text.length > 0){
                if(!mIsEdit) {

                    val detail: OrderDetails = OrderDetails(0,etProductCode.text.toString().toInt(),etQuantityOrdered.text.toString(),etPriceEach.text.toString()
                        ,etOrderLineNumber.text.toString().toInt())
                    presenter.insertData(detail)
                }else{
                    val detail: OrderDetails = OrderDetails(ordDtl!!.orderNumber,etProductCode.text.toString().toInt(),etQuantityOrdered.text.toString(),etPriceEach.text.toString(),etOrderLineNumber.text.toString().toInt())
                    presenter.updateData(detail)
                }
                dismiss()
            }
        }
    }

    fun clear(){

        etProductCode?.setText("")
        etQuantityOrdered?.setText("")
        etPriceEach?.setText("")
        etOrderLineNumber?.setText("")

    }

    fun showDialog(isEdit: Boolean, orderDetail: OrderDetails?){
        ordDtl = orderDetail

        if(ordDtl != null){

            etProductCode?.setText(orderDetail?.productCode.toString())
            etQuantityOrdered?.setText(orderDetail?.quantityOrdered.toString())
            etPriceEach?.setText(orderDetail?.priceEach.toString())
            etOrderLineNumber?.setText(orderDetail?.orderLineNumber.toString())

        }else{
            clear()
        }

        mIsEdit = isEdit
        show()
    }

}