package com.example.classicmodelsmobile.view

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import com.example.classicmodelsmobile.presenter.OrderDetailsPresenter

/**
 * Pop up dialog for adding or editing an order detail
 * @param mIsEdit is set from the user interface (dictates weather or not the order detail is being created or edited)
 */


class DialogOrderDetails : Dialog {
    val ctx: Context = context
    private val btnSubmit: Button
    private val etProductCode: EditText?
    private val etQuantityOrdered: EditText?
    private val etPriceEach: EditText?
    private val etOrderLineNumber: EditText?

    var mIsEdit: Boolean = false
    var ordDtl: OrderDetails? = null

    //TODO: Is selected order necessary?
    constructor(context: Context, presenter: OrderDetailsPresenter, selectedOrder: Order) : super(context) {

        setContentView(R.layout.create_detail_dialog)

        btnSubmit = findViewById(R.id.btnSubmit)
        etProductCode = findViewById(R.id.etProductCode)
        etQuantityOrdered = findViewById(R.id.etQuantityOrdered)
        etPriceEach = findViewById(R.id.etPriceEach)
        etOrderLineNumber = findViewById(R.id.etOrderLineNumber)

        btnSubmit.setOnClickListener {
            if (etProductCode.text.isNotEmpty() && etPriceEach.text.isNotEmpty()) {
                if (!mIsEdit) {

                    val detail = OrderDetails(
                        selectedOrder.orderNumber,
                        etProductCode.text.toString(),
                        etQuantityOrdered.text.toString(),
                        etPriceEach.text.toString(),
                        etOrderLineNumber.text.toString().toInt()
                    )
                    presenter.insertData(detail)

                } else {
                    val detail = OrderDetails(
                        ordDtl!!.orderNumber,
                        etProductCode.text.toString(),
                        etQuantityOrdered.text.toString(),
                        etPriceEach.text.toString(),
                        etOrderLineNumber.text.toString().toInt()
                    )
                    presenter.updateData(detail)
                }

                //Repopulate
                presenter.getAllData(selectedOrder)

                dismiss()
            }
        }
    }

    fun clear() {

        etProductCode?.setText("")
        etQuantityOrdered?.setText("")
        etPriceEach?.setText("")
        etOrderLineNumber?.setText("")

    }

    fun showDialog(isEdit: Boolean, orderDetail: OrderDetails?) {
        ordDtl = orderDetail

        if (ordDtl != null) {

            etProductCode?.setText(orderDetail?.productCode.toString())
            etQuantityOrdered?.setText(orderDetail?.quantityOrdered.toString())
            etPriceEach?.setText(orderDetail?.priceEach.toString())
            etOrderLineNumber?.setText(orderDetail?.orderLineNumber.toString())

        } else {
            clear()
        }

        mIsEdit = isEdit
        show()
    }

}