package com.example.classicmodelsmobile.view

import android.app.Dialog
import android.content.Context

import android.widget.Button
import android.widget.EditText

import com.example.classicmodelsmobile.model.Order

import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.presenter.OrderPresenter


class DialogOrder : Dialog {
    val ctx: Context = context
    val btnSubmit: Button
    val etCustomerNumber:EditText
    val etOrderDate:EditText
    val etRequiredDate:EditText
    val etShippedDate:EditText
    val etComments:EditText
    val etStatus:EditText

    var mIsEdit:Boolean = false
    var ord: Order? = null

    constructor(context: Context, presenter: OrderPresenter) : super(context) {

        setContentView(R.layout.create_dialog)

        btnSubmit = findViewById(R.id.btnSubmit) as Button
        etOrderDate = findViewById(R.id.etOrderDate) as EditText
        etRequiredDate = findViewById(R.id.etRequiredDate) as EditText
        etShippedDate = findViewById(R.id.etShippedDate) as EditText
        etComments = findViewById(R.id.etComments) as EditText
        etStatus = findViewById(R.id.etStatus) as EditText
        etCustomerNumber = findViewById(R.id.etCustomerNumber) as EditText


        btnSubmit.setOnClickListener {
            if(etOrderDate.text.length > 0 && etStatus.text.length > 0){
                if(!mIsEdit) {

                    val order: Order = Order(0,etOrderDate.text.toString(),etRequiredDate.text.toString(),etShippedDate.text.toString()
                    ,etStatus.text.toString(),etComments.text.toString(), etCustomerNumber.text.toString().toInt())
                    presenter.insertData(order)
                }else{
                    val order: Order = Order(ord!!.orderNumber,etOrderDate.text.toString(),etRequiredDate.text.toString(),etShippedDate.text.toString()
                        ,etStatus.text.toString(),etComments.text.toString(), etCustomerNumber.text.toString().toInt())
                    presenter.updateData(order)
                }
                dismiss()
            }
        }
    }

    fun clear(){

        etComments.setText("")
        etCustomerNumber.setText("")
        etOrderDate.setText("")
        etRequiredDate.setText("")
        etShippedDate.setText("")
        etStatus.setText("")

    }

    fun showDialog(isEdit: Boolean, order: Order?){
        ord = order

        if(ord != null){

            etComments.setText(order?.comments)
            etCustomerNumber.setText(order?.customerNumber.toString())
            etOrderDate.setText(order?.orderDate)
            etRequiredDate.setText(order?.requiredDate)
            etShippedDate.setText(order?.shippedDate)
            etStatus.setText(order?.status)

        }else{
            clear()
        }

        mIsEdit = isEdit
        show()
    }


}