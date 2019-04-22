package com.example.classicmodelsmobile.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.classicmodelsmobile.R
import com.example.classicmodelsmobile.model.Order
import com.example.classicmodelsmobile.model.OrderDetails
import com.example.classicmodelsmobile.presenter.OrderDetailsMvp
import com.example.classicmodelsmobile.presenter.OrderDetailsPresenter
import com.example.classicmodelsmobile.view.DialogOrderDetails
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * Activity class for Order details. The order details table is one order to many order details.
 * A recycler view displays all the order details for the selected order from the main actiivty
 * Implements the OrderDetailsView interface from OrderDetailsMvp
 */

class ActivityOrderDetails : AppCompatActivity(), OrderDetailsMvp.OrderDetailsView {

    var adapter: RvAdapter = RvAdapter(java.util.ArrayList())
    var presenter: OrderDetailsPresenter? = null
    var dialog: DialogOrderDetails? = null //TODO: Change


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)



        rvDetail_OrderDetail.layoutManager = LinearLayoutManager(this)
        rvDetail_OrderDetail.adapter = adapter

        val selectedOrder: Order = intent.getSerializableExtra("selectedOrder") as Order

        supportActionBar?.title = "Order Details for: " + selectedOrder.orderNumber

        presenter = OrderDetailsPresenter(this)
        dialog = DialogOrderDetails(this, presenter!!, selectedOrder)

        presenter?.getAllData(selectedOrder)

        fabAddDetail.setOnClickListener { view ->
            dialog?.clear()
            dialog?.showDialog(false, null)
        }
    }

    // Make a new api get request when the user creates or updates an order detail
    fun repopulate() {
        val selectedOrder: Order = intent.getSerializableExtra("selectedOrder") as Order
        presenter?.getAllData(selectedOrder)
    }

    override fun setData(listOrders: List<OrderDetails>) {
        txtEmptyDetail.visibility = View.GONE
        adapter.update(listOrders as MutableList<OrderDetails>)
    }

    override fun setEmpty() {
        txtEmptyDetail.visibility = View.VISIBLE
    }

    override fun setResult(message: String) {
        Snackbar.make(layRootDetail, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoad: Boolean) {
        refreshDetail.isRefreshing = isLoad
    }

    /**
     * Adapter class for populating the recyclerview and setting event handlers for CRUD operations
     */
    inner class RvAdapter(lsOrderDetails: MutableList<OrderDetails>) : RecyclerView.Adapter<ViewHolder>() {

        var lsOrderDetails: MutableList<OrderDetails> = lsOrderDetails

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(p0.context).inflate(R.layout.item_list, null, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(p0: ViewHolder, position: Int) {

            p0.bindValue(lsOrderDetails[position])

            p0.itemView.vOption!!.setOnClickListener {
                val popUp: PopupMenu = PopupMenu(p0.itemView.context, p0.itemView.vOption)
                popUp.inflate(R.menu.menu_order_details)
                val menuOption: PopupMenu.OnMenuItemClickListener =
                    PopupMenu.OnMenuItemClickListener { menuItem: MenuItem ->
                        when {
                            menuItem.itemId == R.id.menuDeleteDetail -> {
                                this@ActivityOrderDetails.presenter?.deleteData(lsOrderDetails[position])
                                repopulate()
                                true

                            }
                            else -> {
                                this@ActivityOrderDetails.dialog?.showDialog(true, lsOrderDetails[position])
                                repopulate()
                                true
                            }
                        }
                    }

                popUp.setOnMenuItemClickListener(menuOption)
                popUp.show()

            }
        }

        override fun getItemCount() = lsOrderDetails.size

        fun update(lsOrders: MutableList<OrderDetails>) {
            this.lsOrderDetails = lsOrders
            notifyDataSetChanged()
        }
    }

    /**
     * Order details to be displayed in the recyclerview
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindValue(detail: OrderDetails) {

            with(detail) {
                itemView.txtTitle.text = "$productCode"
                itemView.txtDesc.text = "$orderLineNumber"
                itemView.txtDate.text = "$quantityOrdered"

            }
        }
    }

}


