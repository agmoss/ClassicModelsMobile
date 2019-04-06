package com.example.classicmodelsmobile.view

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import com.example.classicmodelsmobile.R.layout.create_dialog
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.presenter.OrderPresenter


class DialogOrder : Dialog {
    val ctx: Context = context
    private val btnSubmit: Button
    private val etOrderNumber: EditText
    private val etCustomerNumber: EditText
    private val etOrderDate: EditText
    private val etRequiredDate: EditText
    private val etShippedDate: EditText
    private val etComments: EditText
    private val etStatus: EditText

    var mIsEdit: Boolean = false
    var ord: Order? = null

    constructor(context: Context, presenter: OrderPresenter) : super(context) {

        setContentView(create_dialog)

        btnSubmit = findViewById(com.example.classicmodelsmobile.R.id.btnSubmit)
        etOrderNumber = findViewById(com.example.classicmodelsmobile.R.id.etOrderNumber)
        etOrderDate = findViewById(com.example.classicmodelsmobile.R.id.etOrderDate)
        etRequiredDate = findViewById(com.example.classicmodelsmobile.R.id.etRequiredDate)
        etShippedDate = findViewById(com.example.classicmodelsmobile.R.id.etShippedDate)
        etComments = findViewById(com.example.classicmodelsmobile.R.id.etComments)
        etStatus = findViewById(com.example.classicmodelsmobile.R.id.etStatus)
        etCustomerNumber = findViewById(com.example.classicmodelsmobile.R.id.etCustomerNumber)

        btnSubmit.setOnClickListener {
            if (etOrderDate.text.isNotEmpty() && etStatus.text.isNotEmpty()) {
                if (!mIsEdit) {

                    val order = Order(
                        etOrderNumber.text.toString().toInt(),
                        etOrderDate.text.toString(),
                        etRequiredDate.text.toString(),
                        etShippedDate.text.toString()
                        ,
                        etStatus.text.toString(),
                        etComments.text.toString(),
                        etCustomerNumber.text.toString().toInt()
                    )
                    presenter.insertData(order)
                } else {

                    val order = Order(
                        ord!!.orderNumber,
                        etOrderDate.text.toString(),
                        etRequiredDate.text.toString(),
                        etShippedDate.text.toString(),
                        etStatus.text.toString(),
                        etComments.text.toString(),
                        etCustomerNumber.text.toString().toInt()
                    )
                    presenter.updateData(order)
                }
                dismiss()
            }
        }
    }

    fun clear() {

        etComments.setText("")
        etCustomerNumber.setText("")
        etOrderDate.setText("")
        etRequiredDate.setText("")
        etShippedDate.setText("")
        etStatus.setText("")

    }

    fun showDialog(isEdit: Boolean, order: Order?) {
        ord = order

        if (ord != null) {

            etComments.setText(order?.comments)
            etCustomerNumber.setText(order?.customerNumber.toString())
            etOrderDate.setText(order?.orderDate.toString())
            etRequiredDate.setText(order?.requiredDate.toString())
            etShippedDate.setText(order?.shippedDate.toString())
            etStatus.setText(order?.status)

        } else {
            clear()
        }

        mIsEdit = isEdit
        show()
    }

}