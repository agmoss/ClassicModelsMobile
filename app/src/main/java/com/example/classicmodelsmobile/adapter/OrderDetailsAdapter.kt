package com.example.classicmodelsmobile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.model.OrderDetails

class OrderDetailsAdapter(
    var orderDetails : ArrayList<OrderDetails>,
    var c:Context,
    val mInflater : LayoutInflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        var v : View = mInflater.inflate(R.layout.lv_orderdetails,null)

        var tvOrderNumber = v.findViewById<TextView>(R.id.tvOrderNumber)
        var tvProductCode = v.findViewById<TextView>(R.id.tvProductCode)

        var orderNumber: Int = orderDetails[position].productCode
        var productCode: Int = orderDetails[position].orderNumber

        tvOrderNumber.text = orderNumber.toString()
        tvProductCode.text = productCode.toString()

        return v

    }

    override fun getItem(position: Int): Any {

        return orderDetails[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()

    }

    override fun getCount(): Int {

        return orderDetails.size
    }
}