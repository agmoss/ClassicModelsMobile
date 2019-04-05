package com.example.classicmodelsmobile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.classicmodelsmobile.model.OrderDetails
import com.example.classicmodelsmobile.presenter.OrderDetailsMvp
import com.example.classicmodelsmobile.presenter.OrderDetailsPresenter
import com.example.classicmodelsmobile.repository.DbHelper
import com.example.classicmodelsmobile.view.DialogOrderDetails
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.item_list.view.*

class ActivityOrderDetails : AppCompatActivity(), OrderDetailsMvp.OrderDetailsView {


    var adapter: ActivityOrderDetails.RvAdapter = RvAdapter(java.util.ArrayList<OrderDetails>())
    var presenter: OrderDetailsPresenter? = null
    var dialog: DialogOrderDetails? = null //TODO: Change


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        rvDetail_OrderDetail.layoutManager = LinearLayoutManager(this)
        rvDetail_OrderDetail.adapter = adapter

        //val app: OrderApplication = this.application as OrderApplication

        var db: DbHelper = DbHelper(this)

        presenter = OrderDetailsPresenter(this, db)
        dialog = DialogOrderDetails(this, presenter!!)

        //presenter?.getAllData() populate!!!

        fabAddDetail.setOnClickListener { view ->
            dialog?.clear()
            dialog?.showDialog(false, null)
        }

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


    inner class RvAdapter(lsOrderDetails: MutableList<OrderDetails>) : RecyclerView.Adapter<ActivityOrderDetails.ViewHolder>() {

        var lsOrderDetails: MutableList<OrderDetails> = lsOrderDetails

        override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ActivityOrderDetails.ViewHolder {
            val view: View = LayoutInflater.from(p0?.context).inflate(R.layout.item_list, null, false)
            return ActivityOrderDetails.ViewHolder(view)
        }

        override fun onBindViewHolder(p0: ActivityOrderDetails.ViewHolder, position: Int) {

            p0?.bindValue(lsOrderDetails[position])

            p0?.itemView?.vOption!!.setOnClickListener {
                val popUp: PopupMenu = PopupMenu(p0?.itemView!!.context, p0.itemView.vOption)
                popUp.inflate(R.menu.menu_more)
                val menuOption: PopupMenu.OnMenuItemClickListener =
                    PopupMenu.OnMenuItemClickListener { menuItem: MenuItem ->
                        if (menuItem.itemId == R.id.menuDelete) {
                            this@ActivityOrderDetails.presenter?.deleteData(lsOrderDetails[position].orderNumber)
                            //TODO: Need a refresh
                            true
                        } else {
                            this@ActivityOrderDetails.dialog?.showDialog(true, lsOrderDetails[position])
                            true
                        }
                    }

                popUp.setOnMenuItemClickListener(menuOption)
                popUp.show()

            }
        }

        override fun getItemCount() = lsOrderDetails.size

        fun update(lsOrders: MutableList<OrderDetails>){
            this.lsOrderDetails = lsOrders
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindValue(detail: OrderDetails) {

            with(detail){
                itemView.txtTitle.text = "$orderNumber"
                itemView.txtDesc.text = "$orderLineNumber"
                itemView.txtDate.text = "$quantityOrdered"

            }
        }
    }

}


